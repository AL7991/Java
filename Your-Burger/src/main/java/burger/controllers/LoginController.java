package burger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/login/error")
	public String loginError(Model model) {
		String message = "error";
		model.addAttribute("error", message);
		return "login";
	}
	
}
