package teodorlazarov.getfood.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "product_types")
public class ProductType extends BaseEntity {

    private String name;
//    private List<Product> products;

    public ProductType() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @OneToMany(mappedBy = "productType")
//    public List<Product> getProducts() {
//        return this.products;
//    }
//
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }
}
