package teodorlazarov.getfood.domain.models.view;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ShoppingCartViewModel {

    private List<OrderItemViewModel> orderItems;
    private LocalDate expiresOn;

    public ShoppingCartViewModel() {
        this.orderItems = new LinkedList<>();
    }

    public List<OrderItemViewModel> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItemViewModel> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDate getExpiresOn() {
        return this.expiresOn;
    }

    public void setExpiresOn(LocalDate expiresOn) {
        this.expiresOn = expiresOn;
    }
}
