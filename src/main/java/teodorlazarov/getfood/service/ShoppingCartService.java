package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.ShoppingCartServiceModel;

public interface ShoppingCartService {

    ShoppingCartServiceModel createShoppingCart();

    ShoppingCartServiceModel findShoppingCartById(String id);

}
