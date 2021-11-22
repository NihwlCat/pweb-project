package iftm.pedro.aproject.dtos;

import iftm.pedro.aproject.entities.Order;
import iftm.pedro.aproject.entities.Payload;

public class PayloadDTO {
    private String id;
    private String username;
    private String destiny;
    private String origin;
    private double distance;
    private double price;
    private OrderDTO order;

    public PayloadDTO() {
    }

    public PayloadDTO(Payload payload) {
        this.id = payload.getId();
        this.username = payload.getUsername();
        this.destiny = payload.getDestiny();
        this.origin = payload.getOrigin();
        this.distance = payload.getDistance();
        this.price = payload.getPrice();
        this.order = new OrderDTO(payload.getOrder());
    }

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

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}
