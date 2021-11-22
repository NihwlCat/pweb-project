package iftm.pedro.aproject.entities.utils;

import iftm.pedro.aproject.entities.Order;
import iftm.pedro.aproject.entities.Product;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductOrderPK implements Serializable {

    private Order order;
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    public Order getOrder() {
        return order;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Product getProduct() {
        return product;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrderPK that = (ProductOrderPK) o;
        return order.equals(that.order) && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
