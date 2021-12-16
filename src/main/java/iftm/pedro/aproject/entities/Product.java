package iftm.pedro.aproject.entities;


import iftm.pedro.aproject.entities.utils.ProductOrder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_PRODUCT")
public class Product implements Serializable {

    private Long id;
    private String name;
    private String description;
    private double price;
    private double weight;
    private String imgUrl;

    private Set<ProductOrder> productOrders = new HashSet<>();

    public Product() {
    }

    public Product(Long id, String name, String description, double price, double weight, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.weight = weight;
        this.imgUrl = imgUrl;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    public Long getId() {
        return id;
    }

    @OneToMany(mappedBy = "primaryKey.product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public Set<ProductOrder> getProductOrders() {
        return productOrders;
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setProductOrders(Set<ProductOrder> orders) {
        this.productOrders = orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Column(name = "IMG_URL")
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

