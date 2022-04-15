package burger.controllers;




import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import burger.Order;
import burger.User;
import burger.data.OrderRepository;


@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	
	
	
	private OrderRepository orderRepo;
	
	@Autowired
	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	
	@GetMapping("/current")
	public String orderFrom(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("user",user);
		return "orderFrom";
	}
	
	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus,@AuthenticationPrincipal User user , Model model) {
		if(errors.hasErrors()) {
			model.addAttribute("user",user);
			return "orderFrom";
		}
		order.setUser(user);		
		orderRepo.save(order);
		sessionStatus.setComplete();
		return "redirect:/end";
	}
	

}
