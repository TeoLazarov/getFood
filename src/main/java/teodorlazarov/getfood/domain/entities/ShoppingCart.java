package teodorlazarov.getfood.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart extends BaseEntity {

    //TODO constraints and validations

    private List<OrderItem> orderItems;
    private LocalDate expiresOn;

    public ShoppingCart() {
        this.orderItems = new LinkedList<>();
    }

    @ManyToMany(targetEntity = OrderItem.class, fetch = FetchType.EAGER)
    @JoinTable(name = "shopping_carts_order_items",
            joinColumns = @JoinColumn(name = "shopping_cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_item_id", referencedColumnName = "id"))
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
