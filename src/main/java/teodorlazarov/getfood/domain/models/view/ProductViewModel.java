package teodorlazarov.getfood.domain.models.view;

import teodorlazarov.getfood.domain.entities.ProductType;

import java.math.BigDecimal;

public class ProductViewModel {

    private String id;
    private String name;
    private String description;
    private ProductType productType;
    private BigDecimal price;
    private double weight;
    private boolean isHidden;

    public ProductViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
