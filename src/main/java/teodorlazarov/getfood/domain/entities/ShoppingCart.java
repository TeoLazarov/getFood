package teodorlazarov.getfood.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
public class ShoppingCart extends BaseEntity {

    private User user;
    private List<OrderItem> items;
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
    @JoinColumn(name = "item", referencedColumnName = "id")
    public List<OrderItem> getItems() {
        return this.items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Column(name = "expires_on", nullable = false)
    public LocalDate getExpiresOn() {
        return this.expiresOn;
    }

    public void setExpiresOn(LocalDate expiresOn) {
        this.expiresOn = expiresOn;
    }
}
