/**
 * 
 */
package gstar.jersey.sparqlr;

import java.io.ByteArrayOutputStream;

import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.sparql.resultset.RDFOutput;
import com.hp.hpl.jena.sparql.resultset.ResultsFormat;



/**
 * @author paul_c
 *
 */
public class SparqlTool {
	private String sparqlQueryString;
	private String sparqlEndpointString = 
			"http://localhost:24000/parliament/sparql"; 
	private String sparqlPrefixString = 		
			"	PREFIX owl:<http://www.w3.org/2002/07/owl#> \n"+
			"	PREFIX skos: <http://www.w3.org/2004/02/skos/core#> \n"+
			//"	PREFIX dc:<http://purl.org/dc/elements/1.1/> \n"+
			"	PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"+
			"	PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"+
			//"	PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#> \n"+
			//"	PREFIX ouext: <http://oracle.com/semtech/jena-adaptor/ext/user-def-function#> \n"+
			//"	PREFIX ORACLE_SEM_FS_NS: <http://oracle.com/semtech#timeout=100,qid=123> \n"+
			//"	PREFIX fn:    <http://www.w3.org/2005/xpath-functions#> \n"+
			//"	PREFIX oext:  <http://oracle.com/semtech/jena-adaptor/ext/function#> \n" +
			"	PREFIX ogc: <http://www.opengis.net/geosparql#> \n" +
			//"	PREFIX afn: <http://jena.hpl.hp.com/ARQ/function#> \n" +
			"	PREFIX crmeh: <http://purl.org/crmeh#> \n" +
			"	PREFIX crm: <http://erlangen-crm.org/101001/> \n" +
			//"	PREFIX orageo: <http://xmlns.oracle.com/rdf/geo/> \n" +
			"	PREFIX geo: <http://www.opengis.net/ont/geosparql#> \n" +
			"	PREFIX sf: <http://www.opengis.net/ont/sf#> \n" +
			"   PREFIX gstar: <http://ld.gstar.archaeogeomancy.net/content/> \n" +
			//"   PREFIX ads: <http://data.archaeologydataservice.ac.uk/10.5284/1000230/> \n" + //TESTING ONLY!!!
			"	PREFIX geof: <http://www.opengis.net/def/geosparql/function/> \n" ;
	
	public String getQueryString(){
		return sparqlQueryString;
	}
	
	public void setQueryString(String newQueryString){
		sparqlQueryString = newQueryString;
	}
	
	public String getEndpoint(){
		return sparqlEndpointString;
	}
	
	public void setEndpointString(String newEndpointString){
		sparqlEndpointString = newEndpointString;
	}
	
	public void appendPrefixString (String newPrefixString){
		sparqlPrefixString = sparqlPrefixString + "\n" + newPrefixString;
	}
	
	public String getPrefixString(){
		return sparqlPrefixString;
	}
	
	public Query buildQry(String sparql){
		Query qry = QueryFactory.create(sparqlPrefixString + "\n" + sparql);
		return qry;
	}
	
	//query the sparql endpoint directly
	public Model doDescribe(Query qry){
		QueryExecution x = QueryExecutionFactory.sparqlService(sparqlEndpointString, qry);
		//process results and hand back
		Model model = ModelFactory.createDefaultModel();
		x.execDescribe(model);
		x.close();
		return model;
	}
	
	public Model doSelect(Query qry){
		QueryExecution x = QueryExecutionFactory.sparqlService(sparqlEndpointString, qry);
		//process results and hand back
		Model model = ModelFactory.createDefaultModel();
		ResultSet results = x.execSelect();
		RDFOutput rdfout = new RDFOutput();
		model = rdfout.asModel(results);
		return model;
	}
	
	public ResultSet doSelectRS(Query qry){
		QueryExecution x = QueryExecutionFactory.sparqlService(sparqlEndpointString, qry);
		//process results and hand back
		ResultSet results = x.execSelect();
		return results;
	}
	
	
	public String formatModel(Model model, Lang thisFormat){
		// The language in which to write the model is specified by the lang argument. 
		// Predefined values are "RDF/XML", "RDF/XML-ABBREV", "N-TRIPLE", "TURTLE", (and "TTL") and "N3". 
		// The default value, represented by null, is "RDF/XML".
		
		// deprecated way:
		// 
		// model.write(byteArrayOutputStream, thisLang);
		// 
		
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		RDFDataMgr.write(out,model,thisFormat);	
		//model.write(out, "TTL"); 
		String formattedResults = out.toString();	
		return formattedResults;
	}
	//general outputter
	public String formatResultSet(ResultSet r, ResultsFormat f){
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		ResultSetFormatter.output(out,r,f);
		String frs=out.toString();
		return frs;
	}
	//html outputter, including stylesheet ref
	public String formatResultSet(ResultSet r, String uri){
		ByteArrayOutputStream out = new  ByteArrayOutputStream();
		ResultSetFormatter.outputAsXML(out,r,uri);
		String frs=out.toString();
		return frs;
	}

}
