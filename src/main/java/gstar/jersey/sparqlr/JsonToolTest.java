package gstar.jersey.sparqlr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonToolTest {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		GeoJsonTool j = new GeoJsonTool();
		URL u = new URL("http://localhost:8080/sparqlr/api/sites/geo/json");
		String out = j.getGeoJson(u);
		System.out.println(out);
		

	}

}
