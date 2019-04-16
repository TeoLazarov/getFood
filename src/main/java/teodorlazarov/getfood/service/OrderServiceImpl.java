package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.Order;
import teodorlazarov.getfood.domain.models.service.AddressServiceModel;
import teodorlazarov.getfood.domain.models.service.OrderServiceModel;
import teodorlazarov.getfood.domain.models.service.ShoppingCartServiceModel;
import teodorlazarov.getfood.domain.models.service.UserServiceModel;
import teodorlazarov.getfood.repository.OrderRepository;
import teodorlazarov.getfood.web.errors.exceptions.NotFoundException;
import teodorlazarov.getfood.web.errors.exceptions.ServiceGeneralException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static teodorlazarov.getfood.constants.Errors.*;

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
    public OrderServiceModel createOrder(String username, String addressId) {
        UserServiceModel user = this.userService.findUserByUsername(username);
        String shoppingCartId = user.getShoppingCart().getId();
        ShoppingCartServiceModel shoppingCartServiceModel = this.shoppingCartService.findShoppingCartById(shoppingCartId);
        List<AddressServiceModel> addresses = user.getAddresses();

        if (shoppingCartServiceModel.getOrderItems().size() <= 0) {
            throw new ServiceGeneralException(SHOPPING_CART_IS_EMPTY_EXCEPTION);
        } else if (this.userHasUnfinishedOrder(username)){
            throw new ServiceGeneralException(USER_HAS_ACTIVE_ORDER_EXCEPTION);
        }

        OrderServiceModel orderServiceModel = new OrderServiceModel();
        orderServiceModel.setOrderItems(shoppingCartServiceModel.getOrderItems());
        orderServiceModel.setUser(user);
        orderServiceModel.setTimeOfOrder(LocalDateTime.now());
        orderServiceModel.setFinished(false);
        orderServiceModel
                .setTotalPrice(shoppingCartServiceModel
                        .getOrderItems()
                        .stream()
                        .map(oi -> oi.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(oi.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        for (AddressServiceModel address : addresses) {
            if (address.getId().equals(addressId)) {
                orderServiceModel.setAddressName(address.getName());
                orderServiceModel.setAddressCity(address.getCity());
                orderServiceModel.setAddressAddress(address.getAddress());
                orderServiceModel.setAddressPhoneNumber(address.getPhoneNumber());
                orderServiceModel.setAddressNotes(address.getNotes());
            }
        }

        //todo check if empty also
        if (orderServiceModel.getAddressCity() == null || orderServiceModel.getAddressAddress() == null ) {
            throw new ServiceGeneralException(ADDRESS_NOT_VALID_EXCEPTION);
        }

        Order order = this.modelMapper.map(orderServiceModel, Order.class);
        order = this.orderRepository.saveAndFlush(order);

        this.shoppingCartService.removeOrderItems(shoppingCartServiceModel.getOrderItems(), shoppingCartServiceModel.getId());

        return this.modelMapper.map(order, OrderServiceModel.class);
    }

    @Override
    public List<OrderServiceModel> findRecentOrdersByUsername(String username) {
        return this.orderRepository
                .findFirst3ByUser_UsernameOrderByTimeOfOrderDesc(username)
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> findAllOrdersByUsername(String username) {
        return this.orderRepository
                .findAllByUser_UsernameOrderByTimeOfOrderDesc(username)
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderServiceModel findOrderById(String id) {
        //TODO check if user is the owner of the order or parameter boolean isAdmin to bypass the check
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND_EXCEPTION));

        return this.modelMapper.map(order, OrderServiceModel.class);
    }

    @Override
    public List<OrderServiceModel> findTodaysOrders() {
        List<Order> orders = this.orderRepository.findTodayOrders(LocalDate.now());

        return orders
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderServiceModel> findAllOrders() {
        return this.orderRepository
                .findAllByOrderByTimeOfOrderDesc()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void orderFinish(String orderId) {
        OrderServiceModel orderServiceModel = this.findOrderById(orderId);
        orderServiceModel.setFinished(true);
        Order order = this.modelMapper.map(orderServiceModel, Order.class);

        this.orderRepository.save(order);
    }

    @Override
    public List<OrderServiceModel> findAllNotFinishedOrderByUsername(String username) {
        List<Order> orders = this.orderRepository.findAllByUser_UsernameAndFinishedIsFalse(username);

        return orders.stream().map(o -> this.modelMapper.map(o, OrderServiceModel.class)).collect(Collectors.toList());
    }

    private boolean userHasUnfinishedOrder(String username){
        return this.orderRepository.findAllByUser_UsernameAndFinishedIsFalse(username).size() > 0;

    }
}
