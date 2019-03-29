package teodorlazarov.getfood.domain.models.service;

import teodorlazarov.getfood.domain.entities.Product;

import java.util.List;

public class ProductTypeServiceModel {

    private String id;
    private String name;
    private List<Product> products;
    private boolean isHidden;

    public ProductTypeServiceModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
