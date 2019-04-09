package teodorlazarov.getfood.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import teodorlazarov.getfood.domain.models.binding.OrderCreateBindingModel;
import teodorlazarov.getfood.service.OrderService;

import java.security.Principal;

@Controller
@PreAuthorize("isAuthenticated()")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders/create")
    public ModelAndView createOrder(@ModelAttribute OrderCreateBindingModel model, ModelAndView modelAndView, Principal principal){
        this.orderService.createOrder(principal.getName(), model.getAddress());
        modelAndView.setViewName("redirect:/orders");

        return modelAndView;
    }
}
