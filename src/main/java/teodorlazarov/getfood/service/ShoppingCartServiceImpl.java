package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.OrderItem;
import teodorlazarov.getfood.domain.entities.ShoppingCart;
import teodorlazarov.getfood.domain.models.service.ShoppingCartServiceModel;
import teodorlazarov.getfood.repository.ShoppingCartRepository;

import java.time.LocalDate;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemService orderItemService;
    private final ModelMapper modelMapper;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, OrderItemService orderItemService, ModelMapper modelMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.orderItemService = orderItemService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ShoppingCartServiceModel createShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setExpiresOn(LocalDate.now().plusDays(5L));

        return this.modelMapper
                .map(this.shoppingCartRepository.saveAndFlush(shoppingCart), ShoppingCartServiceModel.class);
    }

    @Override
    public ShoppingCartServiceModel findShoppingCartById(String id) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Shopping cart not found!"));

        return this.modelMapper.map(shoppingCart, ShoppingCartServiceModel.class);
    }
}
