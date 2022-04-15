package burger.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import burger.Burger;
@Repository
public interface BurgerRepository extends CrudRepository<Burger, Long> {
}
