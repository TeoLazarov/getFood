package teodorlazarov.getfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teodorlazarov.getfood.domain.entities.ShoppingCart;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {

    List<ShoppingCart> findAllByExpiresOn(LocalDate localDate);
}
