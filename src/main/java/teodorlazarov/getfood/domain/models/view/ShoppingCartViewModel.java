package teodorlazarov.getfood.domain.models.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ShoppingCartViewModel {

    private String id;
    private List<OrderItemViewModel> products;
    private LocalDate expiresOn;

    public ShoppingCartViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderItemViewModel> getProducts() {
        return this.products;
    }

    public void setProducts(List<OrderItemViewModel> products) {
        this.products = products;
    }

    public LocalDate getExpiresOn() {
        return this.expiresOn;
    }

    public void setExpiresOn(LocalDate expiresOn) {
        this.expiresOn = expiresOn;
    }
}
