package teodorlazarov.getfood.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import teodorlazarov.getfood.domain.models.binding.AddressCreateBindingModel;
import teodorlazarov.getfood.domain.models.service.AddressServiceModel;
import teodorlazarov.getfood.domain.models.view.AddressViewModel;
import teodorlazarov.getfood.service.AddressService;

import java.security.Principal;

@Controller
public class AddressController {

    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressController(AddressService addressService, ModelMapper modelMapper) {
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/addresses/add")
    public ModelAndView create(ModelAndView modelAndView){
        modelAndView.setViewName("address-add");

        return modelAndView;
    }

    @PostMapping("/addresses/add")
    public ModelAndView createConfirm(@ModelAttribute AddressCreateBindingModel model, ModelAndView modelAndView, Principal principal){
        this.addressService.createAddress(this.modelMapper.map(model, AddressServiceModel.class), principal.getName());

        modelAndView.setViewName("redirect:/profile");

        return modelAndView;
    }

    @GetMapping("/addresses/view/{id}")
    public ModelAndView view(@PathVariable String id, ModelAndView modelAndView, Principal principal){
        if (!this.addressService.userHasAddress(id, principal.getName())){
            modelAndView.setViewName("redirect:/profile");

            return modelAndView;
        }

        AddressViewModel addressViewModel = this.modelMapper.map(this.addressService.findAddressById(id), AddressViewModel.class);

        modelAndView.addObject("address", addressViewModel);
        modelAndView.setViewName("address-view");

        return modelAndView;
    }

    @PostMapping("/addresses/view/{id}")
    public ModelAndView editConfirm(@PathVariable String id, @ModelAttribute AddressCreateBindingModel model, ModelAndView modelAndView, Principal principal){
        if (!this.addressService.userHasAddress(id, principal.getName())){
            modelAndView.setViewName("redirect:/profile");

            return modelAndView;
        }

        AddressServiceModel newAddress = this.modelMapper.map(model, AddressServiceModel.class);
        newAddress.setId(id);

        this.addressService.updateAddress(newAddress, principal.getName());

        modelAndView.setViewName("redirect:/profile");

        return modelAndView;
    }

    @GetMapping("/addresses/view/{id}/delete")
    public ModelAndView delete(@PathVariable String id, ModelAndView modelAndView, Principal principal){
        if (!this.addressService.userHasAddress(id, principal.getName())){
            modelAndView.setViewName("redirect:/profile");

            return modelAndView;
        }

        this.addressService.deleteAddress(id, principal.getName());

        modelAndView.setViewName("redirect:/profile");

        return modelAndView;
    }
}
