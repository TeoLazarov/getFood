package teodorlazarov.getfood.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import teodorlazarov.getfood.domain.models.binding.OrderCreateBindingModel;
import teodorlazarov.getfood.domain.models.view.OrderViewModel;
import teodorlazarov.getfood.service.OrderService;

import javax.validation.Valid;
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
    public ModelAndView createOrder(@Valid @ModelAttribute(name = "model") OrderCreateBindingModel model, BindingResult bindingResult, ModelAndView modelAndView, Principal principal) {
        if(bindingResult.hasErrors()){
            modelAndView.addObject("model", model);
            modelAndView.setViewName("redirect:/cart");
            return modelAndView;
        }

        this.orderService.createOrder(principal.getName(), model.getAddress());
        modelAndView.setViewName("redirect:/orders");

        return modelAndView;
    }

    @GetMapping("/orders")
    public ModelAndView viewOrders(ModelAndView modelAndView, Principal principal) {
        List<OrderViewModel> orders = this.orderService.findAllOrdersByUsername(principal.getName()).stream().map(o -> this.modelMapper.map(o, OrderViewModel.class)).collect(Collectors.toList());

        modelAndView.addObject("orders", orders);
        modelAndView.setViewName("order-all-user");

        return modelAndView;
    }

    @GetMapping("/orders/view/{id}")
    public ModelAndView viewOrder(@PathVariable String id, ModelAndView modelAndView) {
        OrderViewModel order = this.modelMapper.map(this.orderService.findOrderById(id), OrderViewModel.class);

        modelAndView.addObject("order", order);
        modelAndView.setViewName("order-view-user");

        return modelAndView;
    }

    @GetMapping("/admin/orders/today")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView ordersToday(ModelAndView modelAndView) {
        List<OrderViewModel> orders = this.orderService
                .findTodaysOrders()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("orders", orders);
        modelAndView.setViewName("order-admin");

        return modelAndView;
    }

    @GetMapping("/admin/orders/all")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ModelAndView ordersAll(ModelAndView modelAndView) {
        List<OrderViewModel> orders = this.orderService
                .findAllOrders()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("orders", orders);
        modelAndView.setViewName("order-admin");

        return modelAndView;
    }

    @GetMapping("/admin/orders/view/{id}")
    public ModelAndView orderView(@PathVariable String id, ModelAndView modelAndView){
        OrderViewModel order = this.modelMapper.map(this.orderService.findOrderById(id), OrderViewModel.class);

        modelAndView.addObject("order", order);
        modelAndView.setViewName("order-view-admin");

        return modelAndView;
    }

    @GetMapping("/admin/orders/finish/{id}")
    public ModelAndView orderFinish(@PathVariable String id, ModelAndView modelAndView){
        this.orderService.orderFinish(id);

        modelAndView.setViewName("redirect:/admin/orders/today");

        return modelAndView;
    }
}
