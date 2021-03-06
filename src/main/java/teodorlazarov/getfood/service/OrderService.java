package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.OrderServiceModel;

import javax.mail.MessagingException;
import java.util.List;

public interface OrderService {

    OrderServiceModel createOrder(String username, String addressId) throws MessagingException;

    List<OrderServiceModel> findRecentOrdersByUsername(String username);

    List<OrderServiceModel> findAllOrdersByUsername(String username);

    OrderServiceModel findOrderById(String id);

    List<OrderServiceModel> findTodaysOrders();

    List<OrderServiceModel> findAllOrders();

    void orderFinish(String orderId);

    List<OrderServiceModel> findAllNotFinishedOrderByUsername(String username);

}
