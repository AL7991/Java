package burger.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import burger.Ingredient;
@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
