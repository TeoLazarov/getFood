package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.ProductServiceModel;
import teodorlazarov.getfood.domain.models.service.ProductTypeServiceModel;

import java.util.List;

public interface ProductTypeService {

    ProductTypeServiceModel findProductTypeByName(String name);

    ProductTypeServiceModel findProductTypeById(String id);

    List<ProductTypeServiceModel> findAllProducts();
}
