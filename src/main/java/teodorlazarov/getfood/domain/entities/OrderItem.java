package teodorlazarov.getfood.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "orders_items")
public class OrderItem extends BaseEntity{

    //TODO constraints and validations

    private Product product;
    private int quantity;

    public OrderItem() {
    }

    @OneToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
