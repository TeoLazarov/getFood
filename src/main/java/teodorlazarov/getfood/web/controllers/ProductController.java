package teodorlazarov.getfood.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import teodorlazarov.getfood.domain.models.binding.ProductAddToCartBindingModel;
import teodorlazarov.getfood.domain.models.binding.ProductCreateBindingModel;
import teodorlazarov.getfood.domain.models.binding.ProductEditBindingModel;
import teodorlazarov.getfood.domain.models.service.ProductServiceModel;
import teodorlazarov.getfood.domain.models.service.ProductTypeServiceModel;
import teodorlazarov.getfood.domain.models.view.ProductTypeViewModel;
import teodorlazarov.getfood.domain.models.view.ProductViewModel;
import teodorlazarov.getfood.service.ProductService;
import teodorlazarov.getfood.service.ProductTypeService;
import teodorlazarov.getfood.validation.ProductCreateValidator;
import teodorlazarov.getfood.validation.ProductEditValidator;
import teodorlazarov.getfood.web.annotations.PageTitle;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("isAuthenticated()")
public class ProductController {

    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final ModelMapper modelMapper;
    private final ProductCreateValidator productCreateValidator;
    private final ProductEditValidator productEditValidator;

    @Autowired
    public ProductController(ProductService productService, ProductTypeService productTypeService, ModelMapper modelMapper, ProductCreateValidator productCreateValidator, ProductEditValidator productEditValidator) {
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.modelMapper = modelMapper;
        this.productCreateValidator = productCreateValidator;
        this.productEditValidator = productEditValidator;
    }

    @GetMapping("/admin/products/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle(value = "Create Product")
    public ModelAndView create(@ModelAttribute(name = "model") ProductCreateBindingModel model, @ModelAttribute ProductTypeServiceModel types, ModelAndView modelAndView) {
        List<ProductTypeViewModel> productTypeViewModels = this.productTypeService.findAllTypes()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductTypeViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("types", productTypeViewModels);

        modelAndView.addObject("model", model);
        modelAndView.setViewName("product-create");

        return modelAndView;
    }

    @PostMapping("/admin/products/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView createConfirm(@Valid @ModelAttribute(name = "model") ProductCreateBindingModel model, BindingResult bindingResult, ModelAndView modelAndView) throws IOException {
        this.productCreateValidator.validate(model, bindingResult);

        if (bindingResult.hasErrors()){
            modelAndView.addObject("types", this.productTypeService.findAllTypes()
                    .stream()
                    .map(p -> this.modelMapper.map(p, ProductTypeViewModel.class))
                    .collect(Collectors.toList()));

            modelAndView.addObject("model", model);
            modelAndView.setViewName("product-create");
            return modelAndView;
        }

        ProductServiceModel product = this.modelMapper.map(model, ProductServiceModel.class);
        this.productService.createProduct(product, model.getProductType(), model.getImage());

        modelAndView.setViewName("redirect:/admin/products/all");

        return modelAndView;
    }

    @GetMapping("/admin/products/all")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PageTitle(value = "All Products")
    public ModelAndView all(ModelAndView modelAndView) {
        modelAndView.setViewName("product-all");

        return modelAndView;
    }

    @GetMapping(value = "/fetch/products", produces = "application/json")
    @ResponseBody
    public Object fetchProducts() {
        return this.productService
                .findAllProducts()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("Duplicates")
    @GetMapping("/admin/products/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle(value = "Edit Product")
    public ModelAndView edit(@PathVariable String id, ModelAndView modelAndView, @ModelAttribute(name = "model") ProductEditBindingModel model) {
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);
        List<ProductTypeViewModel> types = this.productTypeService
                .findAllTypesExceptTheGivenParameter(productServiceModel.getProductType().getName())
                .stream()
                .map(p -> this.modelMapper.map(p, ProductTypeViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("product", productViewModel);
        modelAndView.addObject("types", types);
        this.modelMapper.map(productViewModel, model);
        modelAndView.addObject("model", model);
        modelAndView.setViewName("product-edit");

        return modelAndView;
    }

    @SuppressWarnings("Duplicates")
    @PostMapping("/admin/products/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editConfirm(@PathVariable String id, @Valid @ModelAttribute(name = "model") ProductEditBindingModel model, BindingResult bindingResult, ModelAndView modelAndView) throws IOException {
        this.productEditValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()){
            ProductServiceModel productServiceModel = this.productService.findProductById(id);
            ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);
            List<ProductTypeViewModel> types = this.productTypeService
                    .findAllTypesExceptTheGivenParameter(productServiceModel.getProductType().getName())
                    .stream()
                    .map(p -> this.modelMapper.map(p, ProductTypeViewModel.class))
                    .collect(Collectors.toList());

            modelAndView.addObject("model", model);
            modelAndView.addObject("product", productViewModel);
            modelAndView.addObject("types", types);
            modelAndView.setViewName("product-edit");

            return modelAndView;
        }

        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        ProductTypeServiceModel productType = this.productTypeService.findProductTypeById(model.getProductType());
        productServiceModel.setProductType(productType);
        this.productService.editProduct(id, productServiceModel, model.getImage());

        modelAndView.setViewName("redirect:/admin/products/all");

        return modelAndView;
    }

    @GetMapping("/menu")
    @PageTitle(value = "Menu")
    public ModelAndView menu(ModelAndView modelAndView) {
        modelAndView.setViewName("menu");

        return modelAndView;
    }

    @GetMapping(value = {"/fetch/menu", "/fetch/menu/{type}"}, produces = "application/json")
    @ResponseBody
    public Object fetchMenuItems(@PathVariable Optional<String> type) {
        if (type.isPresent()) {
            return this.productService
                    .findAllNotHiddenProductsByType(type.get())
                    .stream()
                    .map(p -> this.modelMapper.map(p, ProductViewModel.class))
                    .collect(Collectors.toList());
        } else {
            return this.productService
                    .findAllNotHiddenProducts()
                    .stream()
                    .map(p -> this.modelMapper.map(p, ProductViewModel.class))
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/products/view/{id}")
    @PageTitle(value = "Product")
    public ModelAndView view(@ModelAttribute(name = "model") ProductAddToCartBindingModel model, Model modell, @PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        ProductViewModel product = this.modelMapper.map(productServiceModel, ProductViewModel.class);

        modelAndView.addObject("product", product);
        modelAndView.addObject("model", model);
        modelAndView.setViewName("product-view");

        return modelAndView;
    }
}
