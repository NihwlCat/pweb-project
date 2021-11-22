package iftm.pedro.aproject.controllers;

import iftm.pedro.aproject.dtos.OrderDTO;
import iftm.pedro.aproject.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService service;

    OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> findAll(){
        var body = service.findAll();
        return ResponseEntity.ok(body);
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<OrderDTO> insert(@RequestBody Map<Long, Integer> products){
        var body = service.insert(products);
        return ResponseEntity.status(201).body(body);
    }
}
