package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.entities.ProductType;
import teodorlazarov.getfood.domain.models.service.ProductTypeServiceModel;
import teodorlazarov.getfood.repository.ProductTypeRepository;
import teodorlazarov.getfood.web.errors.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static teodorlazarov.getfood.constants.Errors.PRODUCT_TYPE_NOT_FOUND_EXCEPTION;

@Service
public class ProductTypeServiceImpl implements ProductTypeService{

    private final ProductTypeRepository productTypeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository, ModelMapper modelMapper) {
        this.productTypeRepository = productTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductTypeServiceModel findProductTypeByName(String name) {
        return this.productTypeRepository
                .findByName(name)
                .map(t -> this.modelMapper.map(t, ProductTypeServiceModel.class))
                .orElseThrow(() -> new NotFoundException(PRODUCT_TYPE_NOT_FOUND_EXCEPTION));
    }

    @Override
    public List<ProductTypeServiceModel> findAllTypes() {
        return this.productTypeRepository
                .findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductTypeServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductTypeServiceModel findProductTypeById(String id) {
        ProductType productType = this.productTypeRepository.findById(id).orElseThrow(() -> new NotFoundException(PRODUCT_TYPE_NOT_FOUND_EXCEPTION));
        return this.modelMapper.map(productType, ProductTypeServiceModel.class);
    }
}
