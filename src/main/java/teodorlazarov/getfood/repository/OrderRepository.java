package teodorlazarov.getfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teodorlazarov.getfood.domain.entities.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findFirst3ByUser_UsernameOrderByTimeOfOrderDesc(String username);

    List<Order> findAllByUser_UsernameOrderByTimeOfOrderDesc(String username);
}
