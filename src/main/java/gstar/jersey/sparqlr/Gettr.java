package gstar.jersey.sparqlr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.jena.riot.Lang;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.resultset.ResultsFormat;


public class Gettr extends ApiThing {
	
    //debug
    private String debugmsg;
    public void setDebugmsg(String msg){
    	debugmsg = msg;
    }
    public String getDebugmsg(){
    	return debugmsg;
    }
	

	// the query string for the gettr 
	// plus get/set methods
	private String qrystring;
	public String getQueryString(){
		return qrystring;
	}
	public void setQueryString(String newQueryString){
		qrystring = newQueryString;
	}
	
	// whether the incoming parameters are of type uri (true) or terms (false) 
	// plus get/set methods
	private boolean isUri = false;
	public boolean getParamType(){
		return isUri;
	}
	public void setParamType(boolean newParamType){
		isUri = newParamType;
	}
	
	
	//Constructors
	public Gettr(boolean b){
		isUri = b;
	}
	public Gettr(){
		isUri = false;
	}

	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as the requested media type.
     *
     * @return String in requested media type.
     */

	@GET @Path("/check")
	@Produces
	public String checkIt(@DefaultValue("false") @QueryParam("uri") boolean uri){
		setParamType(uri);
		String retval = "working... ";
		
		if (isUri){
			retval = retval + "uri used";
		}
		else {
			retval = retval + "term used";
		}
		
		return retval;
	}

	@GET
    @Produces(MediaType.APPLICATION_XML)
    public String getIt(@DefaultValue("false") @QueryParam("uri") boolean uri) {
		setParamType(uri);
    	String out = getModelSelect(ResultsFormat.FMT_RDF_XML,qrystring);
    	return out;
    } 

    
    @GET @Path("/rdfxml")
    @Produces(MediaType.APPLICATION_XML)
    public String getRDF(@DefaultValue("false") @QueryParam("uri") boolean uri){
    	setParamType(uri);
    	String out = getModelSelect(ResultsFormat.FMT_RDF_XML,qrystring);
    	return out;	
    }
    
    @GET @Path("/nt")
    @Produces(MediaType.TEXT_PLAIN)
    public String getNT(@DefaultValue("false") @QueryParam("uri") boolean uri){
    	setParamType(uri);
    	String out = getModelSelect(ResultsFormat.FMT_RDF_NT,qrystring);
    	return out;	
    }
    
    @GET @Path("/turtle")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTurtle(@DefaultValue("false") @QueryParam("uri") boolean uri){
    	setParamType(uri);
    	String out = getModelSelect(ResultsFormat.FMT_RDF_TURTLE,qrystring);
    	return out;
    }
    
    @GET @Path("/n3")
    @Produces(MediaType.APPLICATION_XML)
    public String getN3(@DefaultValue("false") @QueryParam("uri") boolean uri){
    	setParamType(uri);
    	String out = getModelSelect(ResultsFormat.FMT_RDF_N3,qrystring);
    	return out;
    }
    
    @GET @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonRDF(@DefaultValue("false") @QueryParam("uri") boolean uri){
    	setParamType(uri);
    	String out = getModelSelect(ResultsFormat.FMT_RS_JSON,qrystring);
    	return out;
    }
    
    @GET @Path("/csv")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCsv(@DefaultValue("false") @QueryParam("uri") boolean uri){
    	setParamType(uri);
    	String out = getModelSelect(ResultsFormat.FMT_RS_CSV,qrystring);
    	return out;
    }
    
    @GET @Path("/html")
    @Produces(MediaType.APPLICATION_XHTML_XML)
    public String getHtml(@DefaultValue("false") @QueryParam("uri") boolean uri){
    	setParamType(uri);
    	String out = getModelSelect(qrystring, "http://localhost:8080/sparqlr/xml-to-html.xsl");
    	return out;
    }
    
    @GET @Path("/geojson")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGeoJson(@DefaultValue("false") @QueryParam("uri") boolean uri) throws JsonParseException, JsonMappingException, IOException{
    	setParamType(uri);
    	String json = getModelSelect(ResultsFormat.FMT_RS_JSON,qrystring);
    	GeoJsonTool j = new GeoJsonTool();
    	String out = j.getGeoJson(json);
    	return out;
    	
    }
     
    // the methods to get sparql results, formatted as specified
    public String getModelDescribe(Lang lang, String sparql){
    	SparqlTool sparqlr = new SparqlTool();
    	Model model = sparqlr.doDescribe(sparqlr.buildQry(sparql));
    	String results = sparqlr.formatModel(model, lang);
    	return results;	
    }
    
    //general gettr for most formats
    public String getModelSelect(ResultsFormat f, String sparql){
    	SparqlTool sparqlr = new SparqlTool();
    	ResultSet rs = sparqlr.doSelectRS(sparqlr.buildQry(sparql));
    	String results = sparqlr.formatResultSet(rs, f);
    	return results;	
    }
    //specialist gettr for html
    public String getModelSelect(String sparql, String uri){
    	SparqlTool sparqlr = new SparqlTool();
    	ResultSet rs = sparqlr.doSelectRS(sparqlr.buildQry(sparql));
    	String results = sparqlr.formatResultSet(rs, uri);
    	return results;	
    }
    
/*
 * original method used a model which mangled structure.
 * Revised method above just creates resultset and formats it
    public String getModelSelect(Lang lang, String sparql){
    	SparqlTool sparqlr = new SparqlTool();
    	Model model = sparqlr.doSelect(sparqlr.buildQry(sparql));
    	String results = sparqlr.formatModel(model, lang);
    	return results;	
    }
*/
    
    /* Filters:
     * Make use of FILTER statements and String functions within SPARQL
     * Not very efficient
     */
    
    
    /*
     * Process parameters
     * These can either be terms in which case handle ucase/lcase and add to a filter
     * or uris in which case add to a WHERE clause, the first clause in the overall WHERE statement 
     */
    

    
    // 	process terms
	public String parseParam(String param, Boolean u) {
    	String parsed = "";
    	String[] tokens = getParams(param);
    	
    	for (int i=0; i < tokens.length; i++){
    		try {
    			if (u){
    				String token = URLDecoder.decode(tokens[i],"UTF-8").toUpperCase();
    				parsed = parsed + " '" + token + "' ";
    			}
    			else {
    				String token = URLDecoder.decode(tokens[i],"UTF-8");
    				parsed = parsed + " '" + token + "' ";
    			}   			
    		} 
    		catch (UnsupportedEncodingException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
    	return parsed;
    }
	
	// process uris
	public String parseParam(String param, String prop){
    	String parsed = "{";
    	String[] tokens = getParams(param);
    	
    	for (int i=0; i < tokens.length; i++){
			//no need to decode uris as it is done automagically somehow...
			String token = tokens[i];
			parsed = parsed + buildQueryTriples(prop,token,"class") +"} \n";
			
			//if not the first or last, prep for next UNION statment
			if ((tokens.length >1) && i < (tokens.length-1)){
				parsed = parsed + "UNION \n"+
						"{ \n";
			}

    	}
    	
		return parsed;
	}
	
	public String[] getParams(String p){
    	String[] tokens = p.split(delims);
    	return tokens;
	}

	//filter handling
    public String addFilter(String filterval){
		return filterval;
    }

    public String addFilter(String filterval, String prop){
    	String filterstring = buildQueryTriples(prop,"?ev",filt);
    	if (filterval == "}" ) {
    		filterstring = filterstring + " \n }";
    		return filterstring;	
    	}
    	else {
    		filterstring = filterstring + "\n" +
					"VALUES ?filtervals { " + filterval + " } \n" +
					"FILTER ( \n" +
					"langMatches(lang(?" + filt + "),'en') && \n" +
					"strstarts(str(?" + filt + "), ?filtervals ) \n" +
					")}";
    		return filterstring;
    	}
    }
    
    
	/*
	 * case for most: 
	 * thing property identifier haslabel label
	 * 
	 * case for periods:
	 * thing broughtintoexistence event fallswithin period haslabel label
	 * 
	 * so:
	 * if p92 case = period
	 * else case = other
	 */
    
    public String buildQueryTriples(String thisProperty, String thisUri, String thisLabel){
    	String triples = "";
    	
    	//check for uri vs parameter
    	if(isUri){
    		thisUri = "<" + thisUri + ">";
    	}
    	
    	//check for temporal
    	if (thisProperty == p92){
    		// it's a temporal relationship
    		triples="\n"+
    				"?" + context + " " + thisProperty + " ?o . \n" +
    				"?o " + p10 + " " + thisUri + ". \n" +
    				thisUri + " rdfs:label ?" + thisLabel + " . \n"; 
    	}
    	else {
    		//it's a.n.other relationship
    		triples = "\n" +
    				"?" + context + " " + thisProperty + " " + thisUri + " . \n" +
    				thisUri + " rdfs:label ?" + thisLabel + " . \n"; 
    	}
    	return triples;
    }
    
    /*
     * sets the query string
     * base query plus class specific WHERE plus a filter statement
     * base query plus uri based WHERE with optional UNION plus class specific WHERE
     *  
     */

    public void setqry(String v, String p, Boolean u, String queryRoot, String queryWhere){
    	// using uris
    	if (isUri){
    		setQueryString(queryRoot + parseParam(v,p) + queryWhere +"}");
    	}
    	// using terms
    	else{
    		setQueryString(queryRoot + queryWhere + addFilter(parseParam(v,u),p)); 
    	}

    	
    }
    
    /*
    public void setqry(String v, String p, Boolean u, String q){
    	setQueryString(q + addFilter(parseParam(v,u),p)); 
    }
    */
    
    
    // sets the query string directly
    public void setqry(String q){
    	setQueryString(q + addFilter("}"));
    }
}
