package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    ProductServiceModel createProduct(ProductServiceModel productServiceModel, String productTypeId);

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findProductById(String id);

    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllNotHiddenProducts();

    List<ProductServiceModel> findAllNotHiddenProductsByType(String productType);
}
