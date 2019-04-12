package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.OrderItem;
import teodorlazarov.getfood.domain.entities.Product;
import teodorlazarov.getfood.domain.models.service.OrderItemServiceModel;
import teodorlazarov.getfood.repository.OrderItemRepository;

import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private static final String ORDER_ITEM_NOT_FOUND_EXCEPTION = "OrderItem not found!";

    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ProductService productService, ModelMapper modelMapper) {
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderItemServiceModel createOrderItem(String productId, Integer quantity) {
        OrderItem orderItem = new OrderItem();
        Product product = this.modelMapper.map(this.productService.findProductById(productId), Product.class);

        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);

        return this.modelMapper.map(this.orderItemRepository.saveAndFlush(orderItem), OrderItemServiceModel.class);
    }

    @Override
    public OrderItemServiceModel findOrderItemById(String id) {
        OrderItem orderItem = this.orderItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(ORDER_ITEM_NOT_FOUND_EXCEPTION));

        return this.modelMapper.map(orderItem, OrderItemServiceModel.class);
    }

    @Override
    public OrderItemServiceModel findOrderItemByProductId(String productId) {
        OrderItem orderItem = this.orderItemRepository.findOrderItemByProduct_Id(productId).orElse(null);

        return this.modelMapper.map(orderItem, OrderItemServiceModel.class);
    }

    @Override
    public OrderItemServiceModel updateOrderItem(OrderItemServiceModel orderItemServiceModel) {
        OrderItem orderItem = this.modelMapper.map(orderItemServiceModel, OrderItem.class);
        orderItem = this.orderItemRepository.saveAndFlush(orderItem);

        return this.modelMapper.map(orderItem, OrderItemServiceModel.class);
    }

    @Override
    public void removeOrderItem(OrderItemServiceModel orderItemServiceModel) {
        this.orderItemRepository.deleteById(orderItemServiceModel.getId());
    }
}
