package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.ShoppingCart;
import teodorlazarov.getfood.domain.models.service.OrderItemServiceModel;
import teodorlazarov.getfood.domain.models.service.ProductServiceModel;
import teodorlazarov.getfood.domain.models.service.ShoppingCartServiceModel;
import teodorlazarov.getfood.repository.ShoppingCartRepository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static final Long SHOPPING_CART_DAYS_BEFORE_EXPIRATION = 5L;
    private static final String SHOPPING_CART_NOT_FOUND_EXCEPTION = "Shopping cart not found!";
    private static final String PRODUCT_QUANTITY_LESS_THAN_MINIMUM_EXCEPTION = "Product quantity cannot be less than 1!";

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final ModelMapper modelMapper;

    @Autowired
    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductService productService, OrderItemService orderItemService, ModelMapper modelMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.orderItemService = orderItemService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ShoppingCartServiceModel createShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setExpiresOn(LocalDate.now().plusDays(SHOPPING_CART_DAYS_BEFORE_EXPIRATION));

        return this.modelMapper
                .map(this.shoppingCartRepository.saveAndFlush(shoppingCart), ShoppingCartServiceModel.class);
    }

    @Override
    public ShoppingCartServiceModel findShoppingCartById(String id) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(SHOPPING_CART_NOT_FOUND_EXCEPTION));
        ShoppingCartServiceModel shoppingCartServiceModel = this.modelMapper.map(shoppingCart, ShoppingCartServiceModel.class);

        shoppingCartServiceModel.setOrderItems(shoppingCart.getItems().stream().map(oi -> this.modelMapper.map(oi, OrderItemServiceModel.class)).collect(Collectors.toList()));

        return shoppingCartServiceModel;
    }

    @Override
    public void addToShoppingCart(String productId, Integer quantity, String shoppingCartId) {
        if (quantity <= 0){
            throw new IllegalArgumentException(PRODUCT_QUANTITY_LESS_THAN_MINIMUM_EXCEPTION);
        }

        ShoppingCartServiceModel shoppingCartServiceModel = this.findShoppingCartById(shoppingCartId);
        ProductServiceModel product = this.productService.findProductById(productId);
        boolean shoppingCartContainsItem = false;

        for (OrderItemServiceModel orderItemServiceModel : shoppingCartServiceModel.getOrderItems()) {
            if (orderItemServiceModel.getProduct().getId().equals(product.getId())){
                orderItemServiceModel.setQuantity(quantity);
                this.orderItemService.updateOrderItem(orderItemServiceModel);
                shoppingCartContainsItem = true;
            }
        }

        if (!shoppingCartContainsItem){
            OrderItemServiceModel orderItem = this.orderItemService.createOrderItem(productId, quantity);
            shoppingCartServiceModel.getOrderItems().add(orderItem);
        }

        this.updateShoppingCart(shoppingCartServiceModel);
    }

    private void updateShoppingCart(ShoppingCartServiceModel shoppingCartServiceModel){
        shoppingCartServiceModel.setExpiresOn(LocalDate.now().plusDays(SHOPPING_CART_DAYS_BEFORE_EXPIRATION));
        ShoppingCart shoppingCart = this.modelMapper.map(shoppingCartServiceModel, ShoppingCart.class);

        this.shoppingCartRepository.saveAndFlush(shoppingCart);
    }

    @Override
    public void removeOrderItem(String orderItemId, String shoppingCartId) {
        OrderItemServiceModel orderItemServiceModel = this.orderItemService.findOrderItemById(orderItemId);
        this.removeOrderItemFromCart(orderItemId, shoppingCartId);

        this.orderItemService.removeOrderItem(orderItemServiceModel);
    }

    @Override
    public void removeOrderItems(List<OrderItemServiceModel> orderItemServiceModels, String shoppingCartId) {
        for (OrderItemServiceModel orderItemServiceModel : orderItemServiceModels) {
            this.removeOrderItemFromCart(orderItemServiceModel.getId(), shoppingCartId);
        }
    }

    private void removeOrderItemFromCart(String orderItemId, String shoppingCartId){
        ShoppingCartServiceModel shoppingCartServiceModel = this.findShoppingCartById(shoppingCartId);
        OrderItemServiceModel orderItemServiceModel = this.orderItemService.findOrderItemById(orderItemId);
        List<OrderItemServiceModel> toBeRemoved = new LinkedList<>();

        for (OrderItemServiceModel orderItem : shoppingCartServiceModel.getOrderItems()) {
            if (orderItem.getId().equals(orderItemServiceModel.getId())){
                toBeRemoved.add(orderItem);
            }
        }

        shoppingCartServiceModel.getOrderItems().removeAll(toBeRemoved);

        this.updateShoppingCart(shoppingCartServiceModel);
    }
}
