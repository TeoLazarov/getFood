package teodorlazarov.getfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import teodorlazarov.getfood.domain.entities.Order;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findFirst3ByUser_UsernameOrderByTimeOfOrderDesc(String username);

    List<Order> findAllByUser_UsernameOrderByTimeOfOrderDesc(String username);

    @Query(value = "SELECT * FROM getfood_db.orders WHERE DATE(time_of_order) = ?1 ORDER BY time_of_order DESC", nativeQuery = true)
    List<Order> findTodayOrders(LocalDate date);

    List<Order> findAllByOrderByTimeOfOrderDesc();

    List<Order> findAllByUser_UsernameAndFinishedIsFalse(String username);
}
