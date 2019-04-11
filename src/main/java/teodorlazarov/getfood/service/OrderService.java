package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.OrderServiceModel;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    OrderServiceModel createOrder(String username, String addressId);

    List<OrderServiceModel> findRecentOrdersByUsername(String username);

    List<OrderServiceModel> findAllOrdersByUsername(String username);

    OrderServiceModel findOrderById(String id);

    List<OrderServiceModel> findTodaysOrders();

    List<OrderServiceModel> findAllOrders();
}
