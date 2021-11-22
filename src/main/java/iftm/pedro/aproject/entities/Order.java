package iftm.pedro.aproject.entities;

import iftm.pedro.aproject.entities.utils.ProductOrder;
import iftm.pedro.aproject.entities.utils.Status;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "TB_ORDER")
public class Order {

    private Long id;
    private Status status;
    private User user; // Lazy
    private Payload payload; // EAGER
    private Set<ProductOrder> productOrders = new HashSet<>(); // EAGER

    public Order() {
    }

    public Order(User user) {
        this.user = user;
        this.status = Status.ORDERED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    public Long getId() {
        return id;
    }

    @OneToMany(mappedBy = "primaryKey.order", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<ProductOrder> getProductOrders() {
        return productOrders;
    }

    @OneToOne(mappedBy = "order")
    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public void setProductOrders(Set<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    public User getUser() {
        return user;
    }

    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addProduct(Product product, int amount){
        productOrders.add(new ProductOrder(product, this, amount));
    }

    public void removeProduct(Product product){
        for (Iterator<ProductOrder> iterator = productOrders.iterator();
             iterator.hasNext(); ) {
            ProductOrder productOrder = iterator.next();

            if (productOrder.getOrder().equals(this) && productOrder.getProduct().equals(product)) {
                iterator.remove();
                productOrder.getProduct().getProductOrders().remove(productOrder);
            }
        }
    }
}
