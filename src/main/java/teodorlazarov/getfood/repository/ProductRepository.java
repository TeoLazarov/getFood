package teodorlazarov.getfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teodorlazarov.getfood.domain.entities.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findAllByHiddenIsFalse();

    List<Product> findAllByHiddenIsFalseAndProductType_Name(String productType);

    @Query(value = "SELECT * FROM products p\n" +
            "JOIN product_types pt on p.product_type_id = pt.id\n" +
            "WHERE pt.name IN ('Burger', 'Salad', 'Pasta', 'Potatoes', 'Dessert') AND p.is_hidden = false\n" +
            "ORDER BY RAND()\n" +
            "LIMIT 4", nativeQuery = true)
    List<Product> indexPageProducts();

    Optional<Product> findByName(String name);
}
