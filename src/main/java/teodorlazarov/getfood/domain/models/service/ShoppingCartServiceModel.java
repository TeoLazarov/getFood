package teodorlazarov.getfood.domain.models.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ShoppingCartServiceModel {

    private String id;
    private List<OrderItemServiceModel> orderItems;
    private LocalDate expiresOn;

    public ShoppingCartServiceModel() {
        this.orderItems = new LinkedList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderItemServiceModel> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItemServiceModel> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDate getExpiresOn() {
        return this.expiresOn;
    }

    public void setExpiresOn(LocalDate expiresOn) {
        this.expiresOn = expiresOn;
    }
}
