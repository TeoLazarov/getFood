package teodorlazarov.getfood.domain.models.service;

import teodorlazarov.getfood.domain.entities.OrderItem;
import teodorlazarov.getfood.domain.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ShoppingCartServiceModel {

    private String id;
    private List<OrderItem> orderItems;
    private LocalDate expiresOn;

    public ShoppingCartServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDate getExpiresOn() {
        return this.expiresOn;
    }

    public void setExpiresOn(LocalDate expiresOn) {
        this.expiresOn = expiresOn;
    }
}
