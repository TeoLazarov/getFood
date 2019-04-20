package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.OrderItemServiceModel;

public interface OrderItemService {

    OrderItemServiceModel createOrderItem(String productId, Integer quantity);

    OrderItemServiceModel findOrderItemById(String id);

    OrderItemServiceModel findOrderItemByProductId(String productId);

    OrderItemServiceModel updateOrderItem(OrderItemServiceModel orderItemServiceModel);

    void removeOrderItem(OrderItemServiceModel orderItemServiceModel);
}
