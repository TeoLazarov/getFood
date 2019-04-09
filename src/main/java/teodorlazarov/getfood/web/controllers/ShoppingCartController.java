package teodorlazarov.getfood.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import teodorlazarov.getfood.domain.models.binding.ProductAddToCartBindingModel;
import teodorlazarov.getfood.domain.models.service.ShoppingCartServiceModel;
import teodorlazarov.getfood.domain.models.service.UserServiceModel;
import teodorlazarov.getfood.domain.models.view.OrderItemViewModel;
import teodorlazarov.getfood.domain.models.view.ShoppingCartViewModel;
import teodorlazarov.getfood.service.ShoppingCartService;
import teodorlazarov.getfood.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.stream.Collectors;

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
    public ModelAndView shoppingCart(ModelAndView modelAndView, Principal principal){
        //TODO make it thinner
        UserServiceModel user = this.userService.findUserByUsername(principal.getName());
        ShoppingCartServiceModel shoppingCartServiceModel = this.shoppingCartService.findShoppingCartById(user.getShoppingCart().getId());
        ShoppingCartViewModel model = this.modelMapper.map(shoppingCartServiceModel, ShoppingCartViewModel.class);
        model.setOrderItems(shoppingCartServiceModel.getOrderItems().stream().map(oi -> this.modelMapper.map(oi, OrderItemViewModel.class)).collect(Collectors.toList()));
        BigDecimal total = model.getOrderItems().stream().map(oi -> oi.getProduct().getPrice().multiply(BigDecimal.valueOf(oi.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);

        modelAndView.addObject("model", model.getOrderItems());
        modelAndView.addObject("total", total);
        modelAndView.setViewName("shopping-cart");
        return modelAndView;
    }

    @PostMapping("/cart/add/")
    public ModelAndView addToShoppingCart(@ModelAttribute ProductAddToCartBindingModel model, ModelAndView modelAndView, Principal principal){
        UserServiceModel user = this.userService.findUserByUsername(principal.getName());
        this.shoppingCartService.addToShoppingCart(model.getId(), model.getQuantity(), user.getShoppingCart().getId());

        modelAndView.setViewName("redirect:/cart");
        return modelAndView;
    }

    @GetMapping("/cart/remove/{id}")
    public ModelAndView removeOrderItem(@PathVariable String id, ModelAndView modelAndView, Principal principal){
        UserServiceModel user = this.userService.findUserByUsername(principal.getName());
        this.shoppingCartService.removeOrderItem(id, user.getShoppingCart().getId());

        modelAndView.setViewName("redirect:/cart");
        return modelAndView;
    }
}
