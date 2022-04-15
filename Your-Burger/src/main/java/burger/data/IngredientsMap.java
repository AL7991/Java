package burger.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import burger.Ingredient;
@Service
public class IngredientsMap{

	private IngredientRepository ingredientRepo;

	@Autowired
	public IngredientsMap(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	public HashMap<String,List<Ingredient>> mapOfIngredients() {
		
	List<Ingredient> ingridients = new ArrayList<>();
	
	ingredientRepo.findAll().forEach(i -> ingridients.add(i));	
	HashMap<String,List<Ingredient>> map = new HashMap<>();
	
	Stream.of(Ingredient.Type.values())
	.forEach(typeOfIngredient -> {
		List<Ingredient> lista = new ArrayList<Ingredient>();
		ingridients.stream()
		.forEach(ingridient -> {
			if(ingridient.getType() == typeOfIngredient) {
				lista.add(ingridient);
			}
		map.put(typeOfIngredient.toString().toLowerCase(),lista);
		});
	});
	return map;
	}
}
