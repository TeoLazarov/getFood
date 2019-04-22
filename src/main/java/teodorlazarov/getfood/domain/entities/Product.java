package teodorlazarov.getfood.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;
    private String description;
    private ProductType productType;
    private BigDecimal price;
    private double weight;
    private boolean isHidden;
    private String image;

    public Product() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(targetEntity = ProductType.class)
    @JoinColumn(name = "product_type_id", referencedColumnName = "id", nullable = false)
    public ProductType getProductType() {
        return this.productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "weight", nullable = false, precision = 2)
    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Column(name = "is_hidden", columnDefinition = "tinyint(1) default 0")
    public boolean isHidden() {
        return this.isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    @Column(name = "image")
    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
