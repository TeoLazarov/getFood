package teodorlazarov.getfood.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import teodorlazarov.getfood.domain.models.binding.UserProfileBindingModel;
import teodorlazarov.getfood.domain.models.binding.UserRegisterBindingModel;
import teodorlazarov.getfood.domain.models.service.UserServiceModel;
import teodorlazarov.getfood.domain.models.view.OrderViewModel;
import teodorlazarov.getfood.domain.models.view.UserProfileViewModel;
import teodorlazarov.getfood.domain.models.view.UserViewModel;
import teodorlazarov.getfood.service.OrderService;
import teodorlazarov.getfood.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, OrderService orderService, ModelMapper modelMapper) {
        this.userService = userService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute(name = "model") UserRegisterBindingModel model, ModelAndView modelAndView) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords don't match!");
        }

        this.userService.register(this.modelMapper.map(model, UserServiceModel.class));

        modelAndView.setViewName("redirect:/login");

        return modelAndView;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView users(ModelAndView modelAndView) {
        modelAndView.setViewName("users");

        return modelAndView;
    }

    //move this to UserApiController and move the business logic out of the controller
    @GetMapping(value = "/fetch/users", produces = "application/json")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object fetchUsers() {
        return this.userService
                .findAllUsers()
                .stream()
                .map(u -> {
                    Set<String> roles = new HashSet<>();
                    u.getRoles().forEach(r -> roles.add(r.getRole()));

                    UserViewModel model = this.modelMapper.map(u, UserViewModel.class);
                    model.setRoles(roles);

                    return model;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView, Principal principal){
        UserDetails userPrincipal = this.userService.loadUserByUsername(principal.getName());
        UserProfileViewModel user = this.modelMapper.map(userPrincipal, UserProfileViewModel.class);
        List<OrderViewModel> recentOrders =  this.orderService.findRecentOrdersByUsername(principal.getName()).stream().map(o -> this.modelMapper.map(o, OrderViewModel.class)).collect(Collectors.toList());

        modelAndView.addObject("user", user);
        modelAndView.addObject("orders", recentOrders);
        modelAndView.setViewName("user-profile");

        return modelAndView;
    }

    @GetMapping("/profile/edit")
    public ModelAndView profileEdit(ModelAndView modelAndView, Principal principal) {
        UserDetails userPrincipal = this.userService.loadUserByUsername(principal.getName());
        UserProfileViewModel user = this.modelMapper.map(userPrincipal, UserProfileViewModel.class);

        modelAndView.addObject("user", user);
        modelAndView.setViewName("user-profile-edit");

        return modelAndView;
    }

    @PostMapping("/profile/edit")
    public ModelAndView profileEditConfirm(@ModelAttribute UserProfileBindingModel model, ModelAndView modelAndView, Principal principal) {
        //TODO check for security issues

        model.setUsername(principal.getName());
        modelAndView.setViewName("redirect:/profile");

        if (!model.getPassword().equals(model.getConfirmPassword())) {
            return modelAndView;

        } else {
            this.userService.editProfile(this.modelMapper.map(model, UserServiceModel.class), model.getOldPassword());
            modelAndView.setViewName("redirect:/profile");

            return modelAndView;
        }
    }

    @GetMapping("/admin/user/{username}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ModelAndView profileViewAdmin(@PathVariable String username, ModelAndView modelAndView){
        UserServiceModel userServiceModel = this.userService.findUserByUsername(username);
        UserProfileViewModel user = this.modelMapper.map(userServiceModel, UserProfileViewModel.class);

        modelAndView.addObject("user", user);
        modelAndView.setViewName("user-view-admin");

        return modelAndView;
    }

    @GetMapping("/admin/user/{username}/change-role/{role}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView userChangeRole(@PathVariable String username, @PathVariable String role, ModelAndView modelAndView){
        this.userService.changeUserRole(username, role);

        modelAndView.setViewName("redirect:/users");

        return modelAndView;
    }
}
