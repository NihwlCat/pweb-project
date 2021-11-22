package iftm.pedro.aproject.entities.utils;

import iftm.pedro.aproject.entities.Order;
import iftm.pedro.aproject.entities.Product;
import iftm.pedro.aproject.entities.utils.ProductOrderPK;
import javax.persistence.*;

@Entity
@Table(name = "TB_PRODUCT_ORDER")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.product",
                joinColumns = @JoinColumn(name = "PRODUCT_ID")),
        @AssociationOverride(name = "primaryKey.order",
                joinColumns = @JoinColumn(name = "ORDER_ID")) })
public class ProductOrder {

    private ProductOrderPK primaryKey = new ProductOrderPK();
    private int productAmount;

    public ProductOrder() {
    }

    public ProductOrder(Product product, Order order, int amount) {
        this.productAmount = amount;
        primaryKey.setProduct(product);
        primaryKey.setOrder(order);
    }

    @Transient
    public Product getProduct(){
        return primaryKey.getProduct();
    }

    @Transient
    public Order getOrder(){
        return primaryKey.getOrder();
    }

    @EmbeddedId
    public ProductOrderPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(ProductOrderPK primaryKey) {
        this.primaryKey = primaryKey;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

}
