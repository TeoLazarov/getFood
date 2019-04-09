package teodorlazarov.getfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teodorlazarov.getfood.domain.entities.OrderItem;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

    Optional<OrderItem> findOrderItemByProduct_Id(String productId);
}
