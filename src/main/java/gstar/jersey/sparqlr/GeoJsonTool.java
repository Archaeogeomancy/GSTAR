package gstar.jersey.sparqlr;

import java.io.IOException;
import java.net.URL;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class GeoJsonTool {
	
	
	// set up objects to do stuff
	ObjectMapper om = new ObjectMapper();
	GeometryFactory gf = JTSFactoryFinder.getGeometryFactory(null);	
	
	public String getGeoJsonString(String wkt){
		WKTReader wktreader = new WKTReader(gf);
		GeometryJSON g = new GeometryJSON();
		String json = "";
		try {
			Geometry geom = wktreader.read(WKTHelper.getWithoutSRID(wkt));
			json = g.toString(geom);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;		
	}
	
	public GeoJsonObject getGeoJsonObject(String json) throws JsonParseException, JsonMappingException, IOException{
		GeoJsonObject o = new ObjectMapper().readValue(json, GeoJsonObject.class);
		return o;
		
	}
	public String getGeoJson(String json) throws JsonParseException, JsonMappingException, IOException{
		JsonNode node = om.readValue(json, JsonNode.class);
		String geojson = processJson(node);
		return geojson;
	}
	
	public String getGeoJson(URL json) throws JsonParseException, JsonMappingException, IOException{
		JsonNode node = om.readValue(json, JsonNode.class);
		String geojson = processJson(node);
		return geojson;		
	}
	
	public String processJson(JsonNode node) throws JsonParseException, JsonMappingException, IOException{
		
		// set variables
		String wktvar = "gWKT";
		String geojson = "";
		GeoJsonObject geoj = null;
		
		//set up nodes
	    JsonNode headNode = node.get("head");
	    JsonNode varsNode = headNode.get("vars");
	    JsonNode resultsNode = node.get("results");
	    JsonNode bindingsNode = resultsNode.get("bindings");
	    
	    //set up a geojson-jackson FeatureCollection for output
	    FeatureCollection fc = new FeatureCollection();
	    
	    for (JsonNode binding : bindingsNode) {
	    	//create a new feature for output
	    	Feature f = new Feature();
	    	for (JsonNode var : varsNode) {
	    		String thisVar = var.asText();
	    		if (thisVar.equals(wktvar)) {
	    			System.out.println("Geometry Found");
	    			//handle geometry
	    			String wktin = binding.path(wktvar).path("value").asText();
	    			geoj = getGeoJsonObject(getGeoJsonString(wktin));
	    			f.setGeometry(geoj);
	    		}
	    		else{
	    			//handle property
	    			f.setProperty(thisVar, binding.path(thisVar).path("value").asText());		
	    		}
	    	}
	    	fc.add(f);
	    }
	    
	    geojson = om.writeValueAsString(fc); 
		return geojson;	
	}
}
