package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.OrderItem;
import teodorlazarov.getfood.domain.entities.Product;
import teodorlazarov.getfood.domain.models.service.OrderItemServiceModel;
import teodorlazarov.getfood.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {

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
    public OrderItemServiceModel createOrderItem(String productId) {
        OrderItem orderItem = new OrderItem();
        Product product = this.modelMapper.map(this.productService.findProductById(productId), Product.class);

        orderItem.setProduct(product);
        orderItem.setQuantity(1);

        return this.modelMapper.map(this.orderItemRepository.saveAndFlush(orderItem), OrderItemServiceModel.class);
    }

    @Override
    public OrderItemServiceModel findOrderItemById(String id) {
        OrderItem orderItem = this.orderItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("OrderItem not found!"));

        return this.modelMapper.map(orderItem, OrderItemServiceModel.class);
    }
}
