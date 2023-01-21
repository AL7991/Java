package words;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
	
	public static List<String> ReadFile(String path) throws IOException{
			List<String> listMain = new ArrayList<String>();
		
			List<String> list = Files.readAllLines(Paths.get(path));
			for(String linia : list) {
				listMain.add(linia.trim().toUpperCase());
			}
			return listMain;

	}
}
