package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.Product;
import teodorlazarov.getfood.domain.entities.ProductType;
import teodorlazarov.getfood.domain.models.service.ProductServiceModel;
import teodorlazarov.getfood.domain.models.service.ProductTypeServiceModel;
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

    @Override
    public ProductServiceModel findProductById(String id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found!"));

        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Product not found!"));

        product.setProductType(this.modelMapper.map(this.productTypeService.findProductTypeByName(productServiceModel.getProductType().getName()), ProductType.class));
        product.setName(productServiceModel.getName());
        product.setDescription(productServiceModel.getDescription());
        product.setPrice(productServiceModel.getPrice());
        product.setWeight(productServiceModel.getWeight());
        product.setHidden(productServiceModel.isHidden());
        product.setImage(productServiceModel.getImage());

        return this.modelMapper.map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllNotHiddenProducts() {
        return this.productRepository
                .findAllByHiddenIsFalse()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductServiceModel> findAllNotHiddenProductsByType(String productType) {
        List<String> types = this.productTypeService.findAllTypes().stream().map(p -> p.getName().toLowerCase()).collect(Collectors.toList());

        if (!types.contains(productType.toLowerCase())) {
            throw new IllegalArgumentException("Product type not found!");
        }

        return this.productRepository
                .findAllByHiddenIsFalseAndProductType_Name(productType)
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }
}
