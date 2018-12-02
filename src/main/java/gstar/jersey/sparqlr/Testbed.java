package gstar.jersey.sparqlr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class Testbed {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		String multi = "value1, value 2, value3";
		String single = "value4";
		List<String> testlists = new ArrayList<String>(Arrays.asList(single.split(",")));
		List<String> testlistm = new ArrayList<String>(Arrays.asList(multi.split(",")));
		System.out.println(buildFilter(testlists, "someproperty"));
		System.out.println(buildFilter(testlistm, "someproperty"));
		
		

	}
	
    public static String buildFilter(List<String> l, String p){
    	String strFilter="\n FILTER (";
    	Iterator<String> it = l.iterator();
    	while (it.hasNext()){
    		String v = it.next();
    		strFilter = strFilter + " regex(?" + p + ",'" + v + "') "; 
    		if (it.hasNext()){
    			strFilter = strFilter + "|| ";
    		}
    		else{
    			strFilter = strFilter + ") ";
    		}
    	}
    	
    	return strFilter;
    }

}
