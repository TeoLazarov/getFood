package teodorlazarov.getfood.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import teodorlazarov.getfood.domain.entities.Order;
import teodorlazarov.getfood.domain.entities.User;
import teodorlazarov.getfood.domain.models.binding.OrderCreateBindingModel;
import teodorlazarov.getfood.domain.models.view.OrderViewModel;
import teodorlazarov.getfood.service.OrderService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("isAuthenticated()")
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/orders/create")
    public ModelAndView createOrder(@ModelAttribute OrderCreateBindingModel model, ModelAndView modelAndView, Principal principal){
        this.orderService.createOrder(principal.getName(), model.getAddress());
        modelAndView.setViewName("redirect:/orders");

        return modelAndView;
    }

    @GetMapping("/orders")
    public ModelAndView viewOrders(ModelAndView modelAndView, Principal principal){
        List<OrderViewModel> orders = this.orderService.findAllOrdersByUsername(principal.getName()).stream().map(o -> this.modelMapper.map(o, OrderViewModel.class)).collect(Collectors.toList());

        modelAndView.addObject("orders", orders);
        modelAndView.setViewName("order-all-user");

        return modelAndView;
    }

    @GetMapping("/orders/view/{id}")
    public ModelAndView viewOrder(@PathVariable String id, ModelAndView modelAndView, Authentication authentication){
        OrderViewModel order = this.modelMapper.map(this.orderService.findOrderById(id), OrderViewModel.class);

        modelAndView.addObject("order", order);
        modelAndView.setViewName("order-view-user");

        return modelAndView;
    }
}
