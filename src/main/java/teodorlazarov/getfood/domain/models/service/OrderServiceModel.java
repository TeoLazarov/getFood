package teodorlazarov.getfood.domain.models.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel {

    private String id;
    private List<OrderItemServiceModel> orderItems;
    private UserServiceModel user;
    private String notes;
    private LocalDateTime timeOfOrder;
    private boolean isFinished;
    private BigDecimal totalPrice;

    public OrderServiceModel() {
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

    public UserServiceModel getUser() {
        return this.user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getTimeOfOrder() {
        return this.timeOfOrder;
    }

    public void setTimeOfOrder(LocalDateTime timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
