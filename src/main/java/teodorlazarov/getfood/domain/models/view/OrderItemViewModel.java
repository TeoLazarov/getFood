package teodorlazarov.getfood.domain.models.view;

public class OrderItemViewModel {

    private String id;
    private ProductViewModel product;
    private int quantity;

    public OrderItemViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductViewModel getProduct() {
        return this.product;
    }

    public void setProduct(ProductViewModel product) {
        this.product = product;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
