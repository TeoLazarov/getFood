package teodorlazarov.getfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teodorlazarov.getfood.domain.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
