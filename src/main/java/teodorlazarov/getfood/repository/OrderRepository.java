package teodorlazarov.getfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teodorlazarov.getfood.domain.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
