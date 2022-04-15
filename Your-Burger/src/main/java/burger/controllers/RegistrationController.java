package burger.controllers;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import burger.data.UserRepository;
import burger.security.RegistrationForm;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
private UserRepository userRepo;
private PasswordEncoder passwordEncoder;

public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder) {
	this.userRepo = userRepo;
	this.passwordEncoder = passwordEncoder;
}
	
	@GetMapping
	public String registerForm(Model model) {
		model.addAttribute("form", new RegistrationForm());
		return "registration";
	}
	
	@PostMapping
	public String processRegistration(@Valid @ModelAttribute("form") RegistrationForm form,Errors errors) {	
		
	if(errors.hasErrors()) {
		return "registration";
	}
	
	if(userRepo.findByUsername(form.getUsername()) != null) {
		return "registration";
	}
	
		userRepo.save(form.toUser(passwordEncoder));
		return "redirect:/login";
	}
	
	
	
}
