package teodorlazarov.getfood.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
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

    @GetMapping("/orders/create")
    public ModelAndView createOrder(ModelAndView modelAndView, Principal principal){
        this.orderService.createOrder(principal.getName());
        modelAndView.setViewName("redirect:/orders");

        return modelAndView;
    }
}
