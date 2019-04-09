package teodorlazarov.getfood.domain.models.service;

import teodorlazarov.getfood.domain.entities.ProductType;

import java.math.BigDecimal;

public class ProductServiceModel {

    private String id;
    private String name;
    private String description;
    private ProductTypeServiceModel productType;
    private BigDecimal price;
    private double weight;
    private boolean isHidden;
    private String image;

    public ProductServiceModel() {
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

    public ProductTypeServiceModel getProductType() {
        return this.productType;
    }

    public void setProductType(ProductTypeServiceModel productType) {
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

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
