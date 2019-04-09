package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.Order;
import teodorlazarov.getfood.domain.entities.User;
import teodorlazarov.getfood.domain.models.service.OrderServiceModel;
import teodorlazarov.getfood.domain.models.service.ShoppingCartServiceModel;
import teodorlazarov.getfood.domain.models.service.UserServiceModel;
import teodorlazarov.getfood.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserService userService, ShoppingCartService shoppingCartService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderServiceModel createOrder(String username) {
        UserServiceModel user = this.userService.findUserByUsername(username);
        String shoppingCartId = user.getShoppingCart().getId();
        ShoppingCartServiceModel shoppingCartServiceModel = this.shoppingCartService.findShoppingCartById(shoppingCartId);

        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setOrderItems(shoppingCartServiceModel.getOrderItems());
        orderServiceModel.setUser(user);
        orderServiceModel.setTimeOfOrder(LocalDateTime.now());
        orderServiceModel.setFinished(false);
        orderServiceModel.setTotalPrice(shoppingCartServiceModel.getOrderItems().stream().map(oi -> oi.getProduct().getPrice().multiply(BigDecimal.valueOf(oi.getQuantity()))).reduce(BigDecimal.ZERO,BigDecimal::add));

        Order order = this.modelMapper.map(orderServiceModel, Order.class);
        order = this.orderRepository.saveAndFlush(order);

        this.shoppingCartService.removeOrderItems(shoppingCartServiceModel.getOrderItems(), shoppingCartServiceModel.getId());

        return this.modelMapper.map(order, OrderServiceModel.class);
    }
}
