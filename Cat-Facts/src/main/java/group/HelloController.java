package group;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloController {

	Logic logic = new Logic();
	
	@Autowired
	public HelloController(Logic logic) {
		this.logic = logic;
	}


	@GetMapping
	public String hello(Model model) {
		
		try {
			model.addAttribute("text",logic.catFactText());
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			model.addAttribute("img",logic.catFactImg());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "home";
	}
	
}
