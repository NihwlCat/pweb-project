package iftm.pedro.aproject.services;

import iftm.pedro.aproject.dtos.ProductDTO;
import iftm.pedro.aproject.entities.Product;
import iftm.pedro.aproject.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository repository;

    ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public Page<ProductDTO> findAll(Pageable pageable){
        return repository.findAll(pageable).map(ProductDTO::new);
    }

    public ProductDTO findById(Long id){
        Product p =  repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        return new ProductDTO(p);
    }

    private void dtoToEntity(ProductDTO dto, Product product){
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImgUrl(dto.getImgUrl());
        product.setPrice(dto.getPrice());
        product.setWeight(dto.getWeight());
    }

    public ProductDTO insert(ProductDTO dto){
        Product product = new Product();
        dtoToEntity(dto, product);
        return new ProductDTO(repository.save(product));
    }

    public void update(ProductDTO dto, Long id){
        Product product = repository.getById(id);
        dtoToEntity(dto, product);
        repository.save(product);
    }

    public void delete(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
