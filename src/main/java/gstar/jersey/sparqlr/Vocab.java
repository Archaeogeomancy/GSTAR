package gstar.jersey.sparqlr;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("vocab")
public class Vocab extends ApiThing {
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "This is a vocab resource";
    }
    
    // list configured vocabularies

    @Path("/list")
    public Gettr GettrVocabs(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuery(p2,vocabtype));
    	// thisGettr.setQueryString(qryvocablist);
    	return thisGettr;
    }
    
    // get the json url for a specified vocabulary
    
    @Path("/url/{val}")
    public Gettr GettrJsonUrl(@DefaultValue("http://localhost:8080/sparqlr/api/vocab/list/json") @PathParam("val") String v){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuery(thisGettr.parseParam(v,false)));
    	return thisGettr;
    }
    
    // get related terms using skos relationships
    
    @Path("/skos/related/{val}")
    public Gettr GettrSkosRelatedMatch(@PathParam("val") String v){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuerySkos("relatedMatch", thisGettr.parseParam(v,false)));
    	return thisGettr;
    }
    
    @Path("/skos/exact/{val}")
    public Gettr GettrSkosExactMatch(@PathParam("val") String v){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuerySkos("exactMatch", thisGettr.parseParam(v,false)));
    	return thisGettr;
    }
    
    @Path("/skos/close/{val}")
    public Gettr GettrSkosCloseMatch(@PathParam("val") String v){
    	Gettr thisGettr = new Gettr();

    	thisGettr.setQueryString(buildQuerySkos("closeMatch", thisGettr.parseParam(v,false)));
    	return thisGettr;
    }
    
    @Path("/skos/broader/{val}")
    public Gettr GettrSkosBroader(@PathParam("val") String v){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuerySkos("broader", thisGettr.parseParam(v,false)));
    	return thisGettr;
    }
    
    @Path("/skos/narrower/{val}")
    public Gettr GettrSkosNarrower(@PathParam("val") String v){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuerySkos("narrower", thisGettr.parseParam(v,false)));
    	return thisGettr;
    }
    
    @Path("/skos/relation/semantic/{val}")
    public Gettr GettrSkosSemantic(@PathParam("val") String v){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuerySkos("semanticRelation", thisGettr.parseParam(v,false)));
    	return thisGettr;
    }
    
    @Path("/skos/relation/mapping/{val}")
    public Gettr GettrSkosMapping(@PathParam("val") String v){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuerySkos("mappingRelation", thisGettr.parseParam(v,false)));
    	return thisGettr;
    }
    
    // get a json array of terms for:
    
    // monuments:
    
    @Path("/monument/types")
    public Gettr GettrVocabSiteType(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuery(p2,montype));
    	// thisGettr.setQueryString(qrymontypes);
    	return thisGettr;
    }
    @Path("/monument/periods")
    public Gettr GettrVocabSitePeriod(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuery(p10,monperiod));
    	// thisGettr.setQueryString(qrymontypes);
    	return thisGettr;
    }
    
    // museum artefacts:
    
    @Path("/artefact/types")
    public Gettr GettrVocabArtefactType(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuery(p2,artefacttype));
    	// thisGettr.setQueryString(qryartefacttypes);
    	return thisGettr;
    }
    @Path("/artefact/periods")
    public Gettr GettrVocabArtefactPeriod(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuery(p10,artefactperiod));
    	// thisGettr.setQueryString(qryartefacttypes);
    	return thisGettr;
    }
    @Path("/artefact/materials")
    public Gettr GettrVocabArtefactMaterial(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setQueryString(buildQuery(p45,artefactmaterial));
    	// thisGettr.setQueryString(qryartefactmaterials);
    	return thisGettr;
    }
    
    // query string builders
    
    public String buildQuery(String p, String v){
    	String qryString =  "SELECT ?classification ?vocabterm (Count(?obj) AS ?occurence) ?vocabulary \n"+ 
							"WHERE \n"+
							" { \n"+
								"?obj " + p + " ?classification . \n"+
								"?classification crm:P2_has_type " + v + " . \n"+
								"?classification rdfs:label ?vocabterm . \n"+
								"?classification crm:P2_has_type ?vocabtype . \n"+
								"?vocabtype rdfs:label ?vocabulary . \n"+
							"} \n"+
							"GROUP BY ?classification ?vocabterm ?vocabulary ORDER BY ?vocabterm"; 
    	return qryString;
    }
    
    public String buildQuery(String vocabulary){
    	String qryString = "SELECT ?vocabulary ?jsonurl \n"+
    						"WHERE \n"+
    						"{ \n"+ 
    						"?obj crm:P3_has_note ?json . \n"+ 
    						"?obj rdfs:label ?vocabulary . \n"+
    						"?obj rdfs:label " + vocabulary + "@en . \n"+
    						"?json crm:P2_has_type gstar:E55_JsonUrl . \n"+ 
    						"?json rdfs:label ?jsonurl . \n"+ 
    						"}";
    	return qryString;
    }
    
    public String buildQuerySkos(String p, String v){
    	String qryString = "SELECT DISTINCT ?term  \n"+
    						"WHERE \n"+
    						"{ \n"+
    						"?term1 skos:" + p + " ?term2 . \n"+
    						"?term1 ?rel ?term2 . \n"+
    						"?term1 rdfs:label " + v + "@en . \n"+
    						"?term2 rdfs:label ?term . \n"+
    						"} \n"+
    						"ORDER BY ?term";
    	return qryString;
    }
    

    

}
