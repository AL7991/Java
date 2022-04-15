package burger.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import burger.data.Geocoding;


@Controller
@RequestMapping("/end")
public class EndController {
	
	Geocoding geocoding;
	
	
	@Autowired
	public EndController(Geocoding geocoding) {
		this.geocoding = geocoding;
	}



	@GetMapping
	public String end(Model model) {
		
			double[] latLng = geocoding.getAddress();

			if(latLng != null) {
				model.addAttribute("lat", latLng[0]);
				model.addAttribute("lng", latLng[1]);
			}else {
				model.addAttribute("lat", null);
				model.addAttribute("lng", null);
			}
		
		return "end";
	}
	
}
