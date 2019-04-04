package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.OrderItemServiceModel;

public interface OrderItemService {

    OrderItemServiceModel createOrderItem(String productId);

    OrderItemServiceModel findOrderItemById(String id);
}
