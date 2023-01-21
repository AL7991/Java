package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import words.ReadFile;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		try {
			List<String> lista = ReadFile.ReadFile("text.txt");
			List<String> lista2 = ReadFile.ReadFile("WinWords.txt");
			model.addAttribute("list", lista);
			model.addAttribute("list2", lista2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "home";
	}
	
}
