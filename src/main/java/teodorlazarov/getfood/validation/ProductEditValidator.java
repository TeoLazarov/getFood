package teodorlazarov.getfood.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import teodorlazarov.getfood.domain.entities.Product;
import teodorlazarov.getfood.domain.models.binding.ProductEditBindingModel;
import teodorlazarov.getfood.repository.ProductRepository;

import static teodorlazarov.getfood.validation.ValidationConstants.*;

@Component
public class ProductEditValidator implements org.springframework.validation.Validator {

    private final ProductRepository productRepository;

    @Autowired
    public ProductEditValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductEditBindingModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductEditBindingModel productEditBindingModel = (ProductEditBindingModel) target;

        if (this.productRepository.findByName(productEditBindingModel.getName()).isPresent()){
            Product product = this.productRepository.findByName(productEditBindingModel.getName()).orElse(null);
            if (product != null && !product.getId().equals(productEditBindingModel.getId())){
                errors.rejectValue("name", PRODUCT_ALREADY_EXISTS_ERROR, PRODUCT_ALREADY_EXISTS_ERROR);
            }
        }

        if (!productEditBindingModel.getImage().isEmpty() && productEditBindingModel.getImage().getSize() > MAX_IMAGE_SIZE){
            errors.rejectValue("image", IMAGE_MAX_FILE_SIZE_ERROR, IMAGE_MAX_FILE_SIZE_ERROR);
        }
    }
}
