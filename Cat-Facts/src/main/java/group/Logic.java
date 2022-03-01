package group;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.CatFacts;


@Component
public class Logic {
	
	public String catFactText() throws IOException {
	
	URL url = new URL("https://cat-fact.herokuapp.com/facts/random");
	InputStreamReader readerCatFact = new InputStreamReader(url.openStream());
	CatFacts catFact = new Gson().fromJson(readerCatFact, CatFacts.class);	
	return catFact.getText();
	
	}
	public String catFactImg() throws IOException {
	
	URL urlCatImage = new URL("https://aws.random.cat/meow");
	InputStreamReader readerCatImage = new InputStreamReader(urlCatImage.openStream());
	JsonObject jsonObject = new JsonParser().parse(readerCatImage).getAsJsonObject();
	return jsonObject.get("file").getAsString();
	
	}
}
