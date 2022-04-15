package burger.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import burger.Order;
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
