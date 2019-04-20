package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import teodorlazarov.getfood.domain.entities.Product;
import teodorlazarov.getfood.domain.entities.ProductType;
import teodorlazarov.getfood.domain.models.service.ProductServiceModel;
import teodorlazarov.getfood.repository.ProductRepository;
import teodorlazarov.getfood.web.errors.exceptions.NotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static teodorlazarov.getfood.constants.Errors.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductTypeService productTypeService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductTypeService productTypeService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.productTypeService = productTypeService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(value = {"menu", "allProducts", "allProductsByType"}, allEntries = true)
    public ProductServiceModel createProduct(ProductServiceModel productServiceModel, String productTypeId, MultipartFile image) throws IOException {
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        product.setProductType(this.modelMapper.map(this.productTypeService.findProductTypeById(productTypeId), ProductType.class));
        product.setImage(this.cloudinaryService.uploadImage(image));

        return this.modelMapper.map(this.productRepository.save(product), ProductServiceModel.class);
    }

    @Override
    @Cacheable(value = "allProducts")
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository
                .findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_EXCEPTION));

        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    @CacheEvict(value = {"menu", "allProducts", "allProductsByType"}, allEntries = true)
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel, MultipartFile image) throws IOException {
        Product product = this.productRepository.findById(id).orElseThrow(() -> new NotFoundException(PRODUCT_NOT_FOUND_EXCEPTION));

        product.setProductType(this.modelMapper.map(this.productTypeService.findProductTypeByName(productServiceModel.getProductType().getName()), ProductType.class));
        product.setName(productServiceModel.getName());
        product.setDescription(productServiceModel.getDescription());
        product.setPrice(productServiceModel.getPrice());
        product.setWeight(productServiceModel.getWeight());
        product.setHidden(productServiceModel.isHidden());

        if (!image.isEmpty()){
            this.cloudinaryService.deleteImage(product.getImage());
            product.setImage(this.cloudinaryService.uploadImage(image));
        }

        return this.modelMapper.map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    @Cacheable(value = "menu")
    public List<ProductServiceModel> findAllNotHiddenProducts() {
        return this.productRepository
                .findAllByHiddenIsFalse()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "allProductsByType")
    public List<ProductServiceModel> findAllNotHiddenProductsByType(String productType) {
        List<String> types = this.productTypeService.findAllTypes().stream().map(p -> p.getName().toLowerCase()).collect(Collectors.toList());

        if (!types.contains(productType.toLowerCase())) {
            throw new NotFoundException(PRODUCT_TYPE_NOT_FOUND_EXCEPTION);
        }

        return this.productRepository
                .findAllByHiddenIsFalseAndProductType_Name(productType)
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductServiceModel> findIndexPageProducts() {
        List<Product> products = this.productRepository.indexPageProducts();

        return products.stream().map(p -> this.modelMapper.map(p, ProductServiceModel.class)).collect(Collectors.toList());
    }

    @Override
    public boolean isProductHidden(String productId) {
        Product product = this.productRepository.findById(productId).orElse(null);

        return product == null || product.isHidden();
    }
}
