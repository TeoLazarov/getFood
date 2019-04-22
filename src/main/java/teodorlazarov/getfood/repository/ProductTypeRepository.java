package teodorlazarov.getfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teodorlazarov.getfood.domain.entities.ProductType;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, String> {

    Optional<ProductType> findByName(String productType);

    List<ProductType> findAllByNameNotOrderByNameAsc(String productType);
}
