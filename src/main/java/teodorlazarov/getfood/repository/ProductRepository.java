package teodorlazarov.getfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teodorlazarov.getfood.domain.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
