package teodorlazarov.getfood.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import teodorlazarov.getfood.domain.models.binding.ProductCreateBindingModel;
import teodorlazarov.getfood.repository.ProductRepository;

import static teodorlazarov.getfood.validation.ValidationConstants.*;

@Component
public class ProductCreateValidator implements org.springframework.validation.Validator {

    private final ProductRepository productRepository;

    @Autowired
    public ProductCreateValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ProductCreateBindingModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductCreateBindingModel productCreateBindingModel = (ProductCreateBindingModel) target;

        if (this.productRepository.findByName(productCreateBindingModel.getName()).isPresent()){
            errors.rejectValue("name", PRODUCT_ALREADY_EXISTS_ERROR, PRODUCT_ALREADY_EXISTS_ERROR);
        }

        if (productCreateBindingModel.getImage().isEmpty()){
            errors.rejectValue("image", SELECT_AN_IMAGE_ERROR, SELECT_AN_IMAGE_ERROR);
        }

        if (productCreateBindingModel.getImage().getSize() > MAX_IMAGE_SIZE){
            errors.rejectValue("image", IMAGE_MAX_FILE_SIZE_ERROR, IMAGE_MAX_FILE_SIZE_ERROR);
        }
    }
}
