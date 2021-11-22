package iftm.pedro.aproject.controllers;

import iftm.pedro.aproject.dtos.ProductDTO;
import iftm.pedro.aproject.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService service;

    ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable){
        var body = service.findAll(pageable);
        return ResponseEntity.ok(body);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        var body = service.findById(id);
        return ResponseEntity.ok(body);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody ProductDTO dto){
        service.update(dto, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping (value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO product){
        product = service.insert(product);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(uri).body(product);
    }
}
