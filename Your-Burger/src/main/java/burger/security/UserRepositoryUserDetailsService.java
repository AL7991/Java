package burger.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import burger.Ingredient;
import burger.User;
import burger.Ingredient.Type;
import burger.data.IngredientRepository;
import burger.data.UserRepository;
@Service
public class UserRepositoryUserDetailsService implements UserDetailsService{

	private UserRepository userRepo;
	private IngredientRepository ingrRepo;
	
	@Autowired
	public UserRepositoryUserDetailsService(UserRepository userRepo , IngredientRepository ingrRepo) {
		this.userRepo = userRepo;
		this.ingrRepo = ingrRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user != null) {
			return user;
		}
		throw new UsernameNotFoundException("User '" + username + "' has not been found.");
	}
	
	@EventListener(classes = ApplicationReadyEvent.class)
	public void fillDatabase() {
		ingrRepo.save(new Ingredient("FLTO", "wheat", Type.WRAP));
		ingrRepo.save(new Ingredient("COTO", "corn", Type.WRAP));
		ingrRepo.save(new Ingredient("GRBF", "beef", Type.PROTEIN));
		ingrRepo.save(new Ingredient("CARN", "chicken", Type.PROTEIN));
		ingrRepo.save(new Ingredient("TMTO", "tomato", Type.VEGGIES));
		ingrRepo.save(new Ingredient("LETC", "lettuce", Type.VEGGIES));
		ingrRepo.save(new Ingredient("CHED", "cheddar", Type.CHEESE));
		ingrRepo.save(new Ingredient("JACK", "brie", Type.CHEESE));
		ingrRepo.save(new Ingredient("SLSA", "ketchup", Type.SAUCE));
		ingrRepo.save(new Ingredient("SRCR", "garlic", Type.SAUCE));
		                                    
	}

}
