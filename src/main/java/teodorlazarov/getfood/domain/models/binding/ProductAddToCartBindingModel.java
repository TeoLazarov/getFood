package teodorlazarov.getfood.domain.models.binding;

public class ProductAddToCartBindingModel {

    private String id;
    private Integer quantity;

    public ProductAddToCartBindingModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
