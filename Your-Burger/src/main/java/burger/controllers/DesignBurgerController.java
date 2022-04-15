package burger.controllers;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import burger.Burger;
import burger.Order;
import burger.data.BurgerRepository;
import burger.data.IngredientsMap;

@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignBurgerController {	
	
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute(name = "design")
	public Burger burger() {
		return new Burger();
	}
	
	private BurgerRepository designRepo;
	private IngredientsMap ingredientsMap;

	@Autowired()
	public DesignBurgerController (BurgerRepository designRepo, IngredientsMap ingredientsMap) {
		this.designRepo = designRepo;
		this.ingredientsMap = ingredientsMap;
	}

	@GetMapping
	public String designTaco(Model model) {
		ingredientsMap.mapOfIngredients().keySet().stream()
		.forEach(typeOfIngredient -> model.addAttribute(typeOfIngredient,ingredientsMap.mapOfIngredients().get(typeOfIngredient)));
		model.addAttribute("design", new Burger());
		return "designBurger";	
	}

	@PostMapping
	public String processDesign(@Valid @ModelAttribute("design") Burger design, Errors errors,@ModelAttribute Order order,Model model) {
		if(errors.hasErrors()) {
			ingredientsMap.mapOfIngredients().keySet().stream()
			.forEach(typeOfIngredient -> model.addAttribute(typeOfIngredient,ingredientsMap.mapOfIngredients().get(typeOfIngredient)));
			return "designBurger";	
		}

		Burger saved = designRepo.save(design);
		order.addDesign(saved);	

		return "redirect:/orders/current";
	}
}
