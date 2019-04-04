package teodorlazarov.getfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teodorlazarov.getfood.domain.entities.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {
}
