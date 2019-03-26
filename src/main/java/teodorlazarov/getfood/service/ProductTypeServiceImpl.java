package teodorlazarov.getfood.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teodorlazarov.getfood.domain.models.ProductTypeServiceModel;
import teodorlazarov.getfood.repository.ProductTypeRepository;

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
                .orElseThrow(() -> new IllegalArgumentException("Product type not found!"));
    }
}
