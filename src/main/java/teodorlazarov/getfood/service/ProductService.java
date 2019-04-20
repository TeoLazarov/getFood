package teodorlazarov.getfood.service;

import org.springframework.web.multipart.MultipartFile;
import teodorlazarov.getfood.domain.models.service.ProductServiceModel;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductServiceModel createProduct(ProductServiceModel productServiceModel, String productTypeId, MultipartFile image) throws IOException;

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findProductById(String id);

    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel, MultipartFile image) throws IOException;

    List<ProductServiceModel> findAllNotHiddenProducts();

    List<ProductServiceModel> findAllNotHiddenProductsByType(String productType);

    List<ProductServiceModel> findIndexPageProducts();

    boolean isProductHidden(String productId);
}
