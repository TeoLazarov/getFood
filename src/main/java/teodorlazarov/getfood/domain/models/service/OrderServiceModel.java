package teodorlazarov.getfood.domain.models.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel {

    private String id;
    private List<OrderItemServiceModel> orderItems;
    private UserServiceModel user;
    private String addressName;
    private String addressCity;
    private String addressAddress;
    private String addressPhoneNumber;
    private String addressNotes;
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

    public String getAddressName() {
        return this.addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressCity() {
        return this.addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressAddress() {
        return this.addressAddress;
    }

    public void setAddressAddress(String addressAddress) {
        this.addressAddress = addressAddress;
    }

    public String getAddressPhoneNumber() {
        return this.addressPhoneNumber;
    }

    public void setAddressPhoneNumber(String addressPhoneNumber) {
        this.addressPhoneNumber = addressPhoneNumber;
    }

    public String getAddressNotes() {
        return this.addressNotes;
    }

    public void setAddressNotes(String addressNotes) {
        this.addressNotes = addressNotes;
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
