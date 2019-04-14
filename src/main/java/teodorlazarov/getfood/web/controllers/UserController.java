package teodorlazarov.getfood.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
import teodorlazarov.getfood.validation.UserProfileEditValidator;
import teodorlazarov.getfood.validation.UserRegisterValidator;
import teodorlazarov.getfood.web.annotations.PageTitle;

import javax.validation.Valid;
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
    private final UserRegisterValidator userRegisterValidator;
    private final UserProfileEditValidator userProfileEditValidator;

    @Autowired
    public UserController(UserService userService, OrderService orderService, ModelMapper modelMapper, UserRegisterValidator userRegisterValidator, UserProfileEditValidator userProfileEditValidator) {
        this.userService = userService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.userRegisterValidator = userRegisterValidator;
        this.userProfileEditValidator = userProfileEditValidator;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @PageTitle(value = "Login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle(value = "Register")
    public ModelAndView register(ModelAndView modelAndView, @ModelAttribute(name = "model") UserRegisterBindingModel model) {
        modelAndView.addObject("model", model);
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute(name = "model") UserRegisterBindingModel model, BindingResult bindingResult, ModelAndView modelAndView) {
        this.userRegisterValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);
            modelAndView.setViewName("register");
            return modelAndView;
        }

        this.userService.register(this.modelMapper.map(model, UserServiceModel.class));

        modelAndView.setViewName("redirect:/login");

        return modelAndView;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle(value = "Users")
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
    @PageTitle(value = "Profile")
    public ModelAndView profile(ModelAndView modelAndView, Principal principal) {
        UserDetails userPrincipal = this.userService.loadUserByUsername(principal.getName());
        UserProfileViewModel user = this.modelMapper.map(userPrincipal, UserProfileViewModel.class);
        List<OrderViewModel> recentOrders = this.orderService.findRecentOrdersByUsername(principal.getName()).stream().map(o -> this.modelMapper.map(o, OrderViewModel.class)).collect(Collectors.toList());

        modelAndView.addObject("user", user);
        modelAndView.addObject("orders", recentOrders);
        modelAndView.setViewName("user-profile");

        return modelAndView;
    }

    @SuppressWarnings("Duplicates")
    @GetMapping("/profile/edit")
    @PageTitle(value = "Edit Profile")
    public ModelAndView profileEdit(ModelAndView modelAndView, @ModelAttribute(name = "model") UserProfileBindingModel model, Principal principal) {
        UserDetails userPrincipal = this.userService.loadUserByUsername(principal.getName());
        UserProfileViewModel user = this.modelMapper.map(userPrincipal, UserProfileViewModel.class);

        this.modelMapper.map(user, model);
        modelAndView.addObject("user", user);
        modelAndView.addObject("model", model);
        modelAndView.setViewName("user-profile-edit");

        return modelAndView;
    }

    @SuppressWarnings("Duplicates")
    @PostMapping("/profile/edit")
    public ModelAndView profileEditConfirm(@Valid @ModelAttribute(name = "model") UserProfileBindingModel model, BindingResult bindingResult, ModelAndView modelAndView, Principal principal) {
        model.setUsername(principal.getName());
        this.userProfileEditValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);
            UserDetails userPrincipal = this.userService.loadUserByUsername(principal.getName());
            UserProfileViewModel user = this.modelMapper.map(userPrincipal, UserProfileViewModel.class);
            modelAndView.addObject("user", user);
            modelAndView.setViewName("user-profile-edit");
            return modelAndView;
        }

        this.userService.editProfile(this.modelMapper.map(model, UserServiceModel.class), model.getOldPassword());
        modelAndView.setViewName("redirect:/profile");

        return modelAndView;
    }

    @GetMapping("/admin/user/{username}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PageTitle(value = "User")
    public ModelAndView profileViewAdmin(@PathVariable String username, ModelAndView modelAndView) {
        UserServiceModel userServiceModel = this.userService.findUserByUsername(username);
        UserProfileViewModel user = this.modelMapper.map(userServiceModel, UserProfileViewModel.class);

        modelAndView.addObject("user", user);
        modelAndView.setViewName("user-view-admin");

        return modelAndView;
    }

    @GetMapping("/admin/user/{username}/change-role/{role}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle(value = "Change Role")
    public ModelAndView userChangeRole(@PathVariable String username, @PathVariable String role, ModelAndView modelAndView) {
        this.userService.changeUserRole(username, role);

        modelAndView.setViewName("redirect:/users");

        return modelAndView;
    }
}
