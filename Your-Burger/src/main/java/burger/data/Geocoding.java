package burger.data;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import burger.Order;


@Service
public class Geocoding{

	OrderRepository orderRepo;
	
	
	@Autowired
	public Geocoding(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}
		public double[] getAddress() {
		ArrayList<Order> list = new ArrayList<Order>();
		orderRepo.findAll().forEach(e -> list.add(e));
		Order lastOrder = list.get(list.size() - 1);
		lastOrder.getStreet();
		lastOrder.getZip();		
		lastOrder.getCity();
		String  address =  lastOrder.getStreet() + ", " + lastOrder.getZip() + " " + lastOrder.getCity();
		
		StringBuilder targetText = new StringBuilder();
		char[] charArrayAdress = address.toCharArray();
		
		for (int i = 0; i < charArrayAdress.length; i++){
		    switch (charArrayAdress[i]){
		        case 'ą': {targetText.append('a'); break;}
		        case 'ć': {targetText.append('c'); break; }
		        case 'ę': {targetText.append('e'); break; }
		        case 'ł': {targetText.append('l'); break; }
		        case 'ń': {targetText.append('n'); break; }
		        case 'ó': {targetText.append('o'); break; }
		        case 'ś': {targetText.append('s'); break; }
		        case 'ź': {targetText.append('z'); break; }
		        case 'ż': {targetText.append('z'); break; }
		        case 'Ą': {targetText.append('A'); break; }
		        case 'Ć': {targetText.append('C'); break; }
		        case 'Ę': {targetText.append('E'); break; }
		        case 'Ł': {targetText.append('L'); break; }
		        case 'Ń': {targetText.append('N'); break; }
		        case 'Ó': {targetText.append('O'); break; }
		        case 'Ś': {targetText.append('S'); break; }
		        case 'Ź': {targetText.append('Z'); break; }
		        case 'Ż': {targetText.append('Z'); break; }
		        default: {targetText.append(charArrayAdress[i]); 
		        break;
		        }
		                      
		    }
		}	
			
		double[] array = new double[2];
		
		try {
			URL newUrl = new URL("http://open.mapquestapi.com/geocoding/v1/address?key=HereIsMyKey&location=" + targetText);
			InputStreamReader newReader = new InputStreamReader(newUrl.openStream());
			
			JsonObject jsonObjectNowy = new JsonParser().parse(newReader).getAsJsonObject();
			
			JsonObject jsonObjectNowy2 =(JsonObject) jsonObjectNowy.get("results").getAsJsonArray().get(0);
			JsonObject jsonObjectNowy3 =(JsonObject)  jsonObjectNowy2.get("locations").getAsJsonArray().get(0);
			JsonObject jsonObjectNowy4 =(JsonObject) jsonObjectNowy3.get("displayLatLng");
			array [0] = jsonObjectNowy4.get("lat").getAsDouble();
			array [1] = jsonObjectNowy4.get("lng").getAsDouble();
	
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			array = null;
		}
	
		return array;
		
		}
}