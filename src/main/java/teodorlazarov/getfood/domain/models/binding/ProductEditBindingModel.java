package teodorlazarov.getfood.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductEditBindingModel {

    private String id;
    private String name;
    private String description;
    private String productType;
    private BigDecimal price;
    private double weight;
    private boolean isHidden;
    private MultipartFile image;

    public ProductEditBindingModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotEmpty(message = "Name cannot be empty.")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters.")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min = 3, max = 250, message = "Name must be between 3 and 250 characters.")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty(message = "Product type cannot be empty.")
    public String getProductType() {
        return this.productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    @NotNull
    @DecimalMin(value = "0.01", message = "Price must be positive number.")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    @DecimalMin(value = "0.01", message = "Weight must be a positive number.")
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
