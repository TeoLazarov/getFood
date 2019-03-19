package teodorlazarov.getfood.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView){
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("register")
    public ModelAndView register(ModelAndView modelAndView){
        modelAndView.setViewName("register");

        return modelAndView;
    }
}
