package iftm.pedro.aproject.services;

import iftm.pedro.aproject.auth.Utils;
import iftm.pedro.aproject.dtos.OrderDTO;
import iftm.pedro.aproject.entities.Order;
import iftm.pedro.aproject.entities.Product;
import iftm.pedro.aproject.entities.User;
import iftm.pedro.aproject.repositories.OrderRepository;
import iftm.pedro.aproject.repositories.ProductRepository;
import iftm.pedro.aproject.repositories.UserRepository;
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
    private final UserRepository userRepository;

    OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll(){
        return orderRepository.findAll().stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAllByUser(){
        User u = userRepository.findByEmail(Utils.getAuthenticated()).orElseThrow(() -> new RuntimeException("NOT FOUND"));
        return u.getOrders().stream().map(OrderDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND"));
        return new OrderDTO(order);
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
        User u =  userRepository.findByEmail(Utils.getAuthenticated()).orElseThrow(() -> new RuntimeException("NOT FOUND"));
        Order order = new Order(u);

        products.forEach((key,value) -> {
            Product product = productRepository.getById(key);
            order.addProduct(product, value);
        });

        var orderSaved = orderRepository.save(order);
        return new OrderDTO(orderSaved);
    }
}
