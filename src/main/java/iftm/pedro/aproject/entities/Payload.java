package iftm.pedro.aproject.entities;

import javax.persistence.*;

@Entity
@Table(name = "TB_PAYLOAD")
public class Payload {
    private String id;
    private String username;
    private String destiny;
    private String origin;
    private double distance;
    private double price;
    private Order order; // EAGER

    public Payload() {
    }

    public Payload(String id, Order order) {
        this.id = id;
        this.order = order;
    }

    @Id
    @Column(name = "PAYLOAD_ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @OneToOne
    @JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID")
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
