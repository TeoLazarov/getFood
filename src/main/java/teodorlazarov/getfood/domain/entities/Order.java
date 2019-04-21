package teodorlazarov.getfood.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private List<OrderItem> orderItems;
    private User user;
    private String addressName;
    private String addressCity;
    private String addressAddress;
    private String addressPhoneNumber;
    private String addressNotes;
    private LocalDateTime timeOfOrder;
    private boolean isFinished;
    private BigDecimal totalPrice;

    public Order() {
    }

    @ManyToMany(targetEntity = OrderItem.class)
    @JoinTable(name = "orders_order_items",
    joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false),
    inverseJoinColumns = @JoinColumn(name = "order_item_id", referencedColumnName = "id", nullable = false))
    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "address_name")
    public String getAddressName() {
        return this.addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    @Column(name = "address_city")
    public String getAddressCity() {
        return this.addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    @Column(name = "address_address")
    public String getAddressAddress() {
        return this.addressAddress;
    }

    public void setAddressAddress(String addressAddress) {
        this.addressAddress = addressAddress;
    }

    @Column(name = "address_phone_number")
    public String getAddressPhoneNumber() {
        return this.addressPhoneNumber;
    }

    public void setAddressPhoneNumber(String addressPhoneNumber) {
        this.addressPhoneNumber = addressPhoneNumber;
    }

    @Column(name = "address_notes")
    public String getAddressNotes() {
        return this.addressNotes;
    }

    public void setAddressNotes(String addressNotes) {
        this.addressNotes = addressNotes;
    }

    @Column(name = "time_of_order", nullable = false)
    public LocalDateTime getTimeOfOrder() {
        return this.timeOfOrder;
    }

    public void setTimeOfOrder(LocalDateTime timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    @Column(name = "is_finished", nullable = false)
    public boolean isFinished() {
        return this.isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @Column(name = "total_price", nullable = false)
    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

}
