package teodorlazarov.getfood.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import teodorlazarov.getfood.domain.models.binding.ProductCreateBindingModel;
import teodorlazarov.getfood.domain.models.service.ProductServiceModel;
import teodorlazarov.getfood.domain.models.service.ProductTypeServiceModel;
import teodorlazarov.getfood.domain.models.view.ProductTypeViewModel;
import teodorlazarov.getfood.domain.models.view.ProductViewModel;
import teodorlazarov.getfood.service.ProductService;
import teodorlazarov.getfood.service.ProductTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasRole('ROLE_EMPLOYEE')")
public class ProductController {

    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ProductTypeService productTypeService, ModelMapper modelMapper) {
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/products/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView create(@ModelAttribute ProductTypeServiceModel model, ModelAndView modelAndView){
        modelAndView.addObject("model", this.productTypeService.findAllProducts()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductTypeViewModel.class)).collect(Collectors.toList()));
        modelAndView.setViewName("product-create");

        return modelAndView;
    }

    @PostMapping("products/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView createConfirm(@ModelAttribute ProductCreateBindingModel model, ModelAndView modelAndView){
        ProductServiceModel product = this.modelMapper.map(model, ProductServiceModel.class);
        if (this.productService.createProduct(product, model.getProductType()) == null){
            modelAndView.setViewName("product-create");

            return modelAndView;
        }

        modelAndView.setViewName("redirect:/products/all");

        return modelAndView;
    }

    @GetMapping("/products/all")
    public ModelAndView all(ModelAndView modelAndView){
        modelAndView.setViewName("product-all");

        return modelAndView;
    }

    @GetMapping(value = "/fetch/products", produces = "application/json")
    @ResponseBody
    public Object fetchProducts(){
        return this.productService
                .findAllProducts()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductViewModel.class))
                .collect(Collectors.toList());
    }
}
