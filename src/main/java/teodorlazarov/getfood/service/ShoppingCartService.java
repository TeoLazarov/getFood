package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.ShoppingCartServiceModel;

public interface ShoppingCartService {

    ShoppingCartServiceModel createShoppingCart();

    ShoppingCartServiceModel findShoppingCartById(String id);

    void addToShoppingCart(String productId, Integer quantity, String shoppingCartId);

    void removeOrderItem(String orderItemId, String shoppingCartId);
}
