package teodorlazarov.getfood.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import teodorlazarov.getfood.domain.binding.UserRegisterBindingModel;
import teodorlazarov.getfood.domain.models.UserServiceModel;
import teodorlazarov.getfood.service.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(ModelAndView modelAndView){
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView){
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute(name = "model") UserRegisterBindingModel model, ModelAndView modelAndView){
        //TODO compare password and confirmPassword
        this.userService.register(this.modelMapper.map(model, UserServiceModel.class));

        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }
}
