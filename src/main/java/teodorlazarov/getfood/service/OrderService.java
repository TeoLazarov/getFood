package teodorlazarov.getfood.service;

import teodorlazarov.getfood.domain.models.service.OrderServiceModel;

public interface OrderService {

    OrderServiceModel createOrder(String username, String addressId);
}
