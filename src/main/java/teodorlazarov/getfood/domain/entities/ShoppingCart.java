package teodorlazarov.getfood.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart extends BaseEntity {

    //TODO constraints and validations

    private User user;
    private List<OrderItem> orderItems;
    private LocalDate expiresOn;

    public ShoppingCart() {
    }

    @OneToOne(mappedBy = "shoppingCart")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = OrderItem.class)
    @JoinColumn(name = "order_item", referencedColumnName = "id")
    public List<OrderItem> getItems() {
        return this.orderItems;
    }

    public void setItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Column(name = "expires_on", nullable = false)
    public LocalDate getExpiresOn() {
        return this.expiresOn;
    }

    public void setExpiresOn(LocalDate expiresOn) {
        this.expiresOn = expiresOn;
    }
}
