package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.ProductTypeServiceModel;

public interface ProductTypeService {

    ProductTypeServiceModel findProductTypeByName(String name);
}
