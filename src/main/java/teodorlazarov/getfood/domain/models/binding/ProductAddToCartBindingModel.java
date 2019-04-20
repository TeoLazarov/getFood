package teodorlazarov.getfood.domain.models.binding;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductAddToCartBindingModel {

    private String id;
    private int quantity;

    public ProductAddToCartBindingModel() {
    }

    @NotEmpty(message = "Missing product id.")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Min(value = 1, message = "Quantity must be between 1 and 10.")
    @Max(value = 10, message = "Quantity must be between 1 and 10.")
    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
