package iftm.pedro.aproject.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import iftm.pedro.aproject.dtos.Address;
import iftm.pedro.aproject.dtos.AddressForm;
import iftm.pedro.aproject.dtos.PayloadDTO;
import iftm.pedro.aproject.entities.Order;
import iftm.pedro.aproject.entities.Payload;
import iftm.pedro.aproject.entities.User;
import iftm.pedro.aproject.entities.utils.Status;
import iftm.pedro.aproject.repositories.OrderRepository;
import iftm.pedro.aproject.repositories.PayloadRepository;
import iftm.pedro.aproject.repositories.UserRepository;
import iftm.pedro.aproject.services.exceptions.ServiceException;
import iftm.pedro.aproject.utils.Utils;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PayloadService {

    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    private final PayloadRepository payloadRepository;
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate = getRestTemplate();

    private static final String HOST_URL = "https://brasilapi.com.br/api/cep/v2/";

    public PayloadService(PayloadRepository payloadRepository, OrderRepository orderRepository) {
        this.payloadRepository = payloadRepository;
        this.orderRepository = orderRepository;
    }

    private Address retrieveData(String cep, int number) throws IOException, RestClientException {
        ResponseEntity<String> response = restTemplate.getForEntity(HOST_URL + "/{cep}",String.class, Map.ofEntries(Map.entry("cep",cep)));
        JsonNode node = new ObjectMapper().readTree(response.getBody());
        String address = node.get("street").asText() +
                ", " + number +
                ". " + node.get("neighborhood").asText() +
                ", " + node.get("city").asText() +
                ", " + node.get("state").asText() + ".";

        var coordinates = node.get("location").get("coordinates");
        Point point = new Point(coordinates.get("longitude").asDouble(), coordinates.get("latitude").asDouble());
        return new Address(address, number, point);
    }

    private Address getAddressFromJson(String json, String path) throws IOException {
        var mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        return mapper.readValue(root.findValue(path).traverse(),Address.class);
    }

    @Transactional(readOnly = true)
    public Page<PayloadDTO> findAll(Pageable pageable){
        return payloadRepository.findAll(pageable).map(PayloadDTO::new);
    }

    @Transactional
    public PayloadDTO insert (Long orderId, String json){
        try {
            Address origin = getAddressFromJson(json, "origin");
            Address destiny = getAddressFromJson(json, "destiny");

            Payload payload = new Payload();
            payload.setId("TX" + orderId); // Provisório
            Order order = orderRepository.getById(orderId);

            order.setStatus(Status.PACKAGED);
            origin = retrieveData(origin.getLocale(), origin.getNumber());
            destiny = retrieveData(destiny.getLocale(), destiny.getNumber());
            createPayload(payload, order, origin, destiny);

        } catch (IOException | RestClientException e) {
            throw new ServiceException("CEP Inválido", HttpStatus.BAD_REQUEST);
        }
        return new PayloadDTO();
    }

    @Transactional
    public void insert(AddressForm form, Long orderId) throws RestClientException, IOException{
        Payload payload = new Payload();
        payload.setId("TX" + orderId); // Provisório
        Order order = orderRepository.getById(orderId);
        Address origin = retrieveData(form.getOriginLocale().replace("-",""), form.getOriginNumber());
        Address destiny = retrieveData(form.getDestinyLocale().replace("-",""), form.getDestinyNumber());
        order.setStatus(Status.PACKAGED);
        createPayload(payload, order, origin, destiny);
    }

    private void createPayload(Payload payload, Order order, Address origin, Address destiny) {
        double distance = Utils.calculateDistance(origin.getCoordinates(), destiny.getCoordinates());
        double price = order.getProductOrders().stream().mapToDouble(ord -> ord.getProduct().getPrice() * ord.getProductAmount()).sum();

        payload.setOrder(order);
        payload.setUsername(iftm.pedro.aproject.auth.Utils.getAuthenticated());
        payload.setOrigin(origin.getLocale());
        payload.setDestiny(destiny.getLocale());
        payload.setDistance(distance);
        payload.setPrice(price);

        payloadRepository.save(payload);
    }

    @Transactional
    public void deliverOrder(String id){
        Payload p = payloadRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND"));
        Order o = p.getOrder();
        o.setStatus(Status.DELIVERED);
        orderRepository.save(o);
        payloadRepository.delete(p);
    }

    @Transactional
    public void delete (String id){
        try {
            payloadRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new RuntimeException("Id non existent" + id);
        } catch (DataIntegrityViolationException e){
            throw new RuntimeException("Integrity Violation. You cannot delete a category with products");
        }
    }

    @Transactional(readOnly = true)
    public List<PayloadDTO> findAllByUser() {
        return payloadRepository.findByUsername(iftm.pedro.aproject.auth.Utils.getAuthenticated())
                .stream().map(PayloadDTO::new).collect(Collectors.toList());
    }

}
