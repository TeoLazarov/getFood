package teodorlazarov.getfood.domain.models.service;

public class OrderItemServiceModel {

    private String id;
    private ProductServiceModel product;
    private int quantity;

    public OrderItemServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductServiceModel getProduct() {
        return this.product;
    }

    public void setProduct(ProductServiceModel product) {
        this.product = product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
