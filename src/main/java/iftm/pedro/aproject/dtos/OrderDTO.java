package iftm.pedro.aproject.dtos;

import iftm.pedro.aproject.entities.Order;
import iftm.pedro.aproject.entities.utils.ProductOrder;

import java.util.HashMap;
import java.util.stream.Collectors;

public class OrderDTO {
    private Long id;
    private String status;
    private double subTotal;
    private double totalWeight;
    private int totalItems;
    private int totalProducts;

    private HashMap<String, Integer> products = new HashMap<>();

    public OrderDTO() {
    }

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.status = order.getStatus().getName();

        order.getProductOrders().forEach(productOrder -> {
            subTotal += (productOrder.getProduct().getPrice() * productOrder.getProductAmount());
            totalWeight += productOrder.getProduct().getWeight();
            totalItems += productOrder.getProductAmount();
            totalProducts++;
        });

        products = (HashMap<String, Integer>) order.getProductOrders()
                .stream().collect(Collectors.toMap(x -> x.getProduct().getId() + ", " + x.getProduct().getName(), ProductOrder::getProductAmount));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Integer> products) {
        this.products = products;
    }
}
