package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    ProductServiceModel createProduct(ProductServiceModel productServiceModel, String productTypeId);

    List<ProductServiceModel> findAllProducts();
}
