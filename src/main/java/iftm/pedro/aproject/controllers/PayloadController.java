package iftm.pedro.aproject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import iftm.pedro.aproject.dtos.Address;
import iftm.pedro.aproject.dtos.OrderDTO;
import iftm.pedro.aproject.dtos.PayloadDTO;
import iftm.pedro.aproject.services.OrderService;
import iftm.pedro.aproject.services.PayloadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/payloads")
public class PayloadController {

    private final PayloadService service;

    PayloadController(PayloadService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PayloadDTO>> findAll(){
        var body = service.findAll();
        return ResponseEntity.ok(body);
    }

    @PostMapping
    public ResponseEntity<Void> insert (@RequestParam Long order, @RequestBody String json){
        var body = service.insert(order, json);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
