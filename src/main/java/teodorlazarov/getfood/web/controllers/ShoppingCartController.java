package teodorlazarov.getfood.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import teodorlazarov.getfood.domain.models.view.ShoppingCartViewModel;
import teodorlazarov.getfood.service.ShoppingCartService;
import teodorlazarov.getfood.service.UserService;

import java.security.Principal;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService, ModelMapper modelMapper) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/cart")
    public ModelAndView shoppingCart(ModelAndView modelAndView){
        modelAndView.setViewName("shopping-cart");

        return modelAndView;
    }
}
