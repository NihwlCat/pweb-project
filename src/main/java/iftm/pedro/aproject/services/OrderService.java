package iftm.pedro.aproject.services;

import iftm.pedro.aproject.dtos.OrderDTO;
import iftm.pedro.aproject.entities.Order;
import iftm.pedro.aproject.entities.Product;
import iftm.pedro.aproject.repositories.OrderRepository;
import iftm.pedro.aproject.repositories.ProductRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    OrderService(OrderRepository orderRepository, ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll(){
        return orderRepository.findAll().stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public void delete (Long id){
        try {
            orderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new RuntimeException("Id non existent" + id);
        } catch (DataIntegrityViolationException e){
            throw new RuntimeException("Integrity Violation. You cannot delete a category with products");
        }
    }

    @Transactional
    public OrderDTO insert(Map<Long, Integer> products){
        Order order = new Order(null);

        products.forEach((key,value) -> {
            Product product = productRepository.getById(key);
            order.addProduct(product, value);
        });

        var orderSaved = orderRepository.save(order);
        return new OrderDTO(orderSaved);
    }
}
