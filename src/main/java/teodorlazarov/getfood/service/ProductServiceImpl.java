package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.Product;
import teodorlazarov.getfood.domain.entities.ProductType;
import teodorlazarov.getfood.domain.models.service.ProductServiceModel;
import teodorlazarov.getfood.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductTypeService productTypeService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductTypeService productTypeService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.productTypeService = productTypeService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel createProduct(ProductServiceModel productServiceModel, String productTypeId) {
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        product.setProductType(this.modelMapper.map(this.productTypeService.findProductTypeById(productTypeId), ProductType.class));

        return this.modelMapper.map(this.productRepository.save(product), ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository
                .findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }
}
