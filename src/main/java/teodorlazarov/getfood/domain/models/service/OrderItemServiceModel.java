package teodorlazarov.getfood.domain.models.service;

import teodorlazarov.getfood.domain.entities.Product;

public class OrderItemServiceModel {

    private String id;
    private Product product;
    private int quantity;

    public OrderItemServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
