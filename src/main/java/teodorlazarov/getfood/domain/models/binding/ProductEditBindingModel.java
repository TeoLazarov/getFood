package teodorlazarov.getfood.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class ProductEditBindingModel {

    private String name;
    private String description;
    private String productType;
    private BigDecimal price;
    private double weight;
    private boolean isHidden;
    private MultipartFile image;

    public ProductEditBindingModel() {
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

    public String getProductType() {
        return this.productType;
    }

    public void setProductType(String productType) {
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

    public MultipartFile getImage() {
        return this.image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
