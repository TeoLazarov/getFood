package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.ProductTypeServiceModel;

public interface ProductTypeService {

    ProductTypeServiceModel findProductTypeByName(String name);
}
