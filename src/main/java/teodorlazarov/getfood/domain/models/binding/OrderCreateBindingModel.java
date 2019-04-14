package teodorlazarov.getfood.domain.models.binding;

import javax.validation.constraints.NotEmpty;

public class OrderCreateBindingModel {

    private String address;

    public OrderCreateBindingModel() {
    }

    @NotEmpty(message = "Address cannot be empty!")
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
