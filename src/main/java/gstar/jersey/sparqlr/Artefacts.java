package gstar.jersey.sparqlr;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("artefacts")
public class Artefacts extends ApiThing {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "This is a site";
    }
    
    @Path("/geo/all")
    public Gettr GettrGeo(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setqry(qrygeoRoot + qrygeoWhereArtefacts);
    	return thisGettr;
    }
    
    /*
     * Old structure when it was possible to get all labels rather than just some
    @Path("/labels")
    public Gettr GettrLabels(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setqry(qrylblRoot + qrylblWhereArtefacts);
    	return thisGettr;
    }
    */
    @Path("/labels/all")
    public String GettrLabels(){
    	return "Please use a more specific url to get labels of a type";
    }    
    
    /* URL path params
     * incoming named 'val'
     * passed to query string function as 'v'
     */
    
    // labels by id, all 
    @Path("/labels/all/id")
    public Gettr GettrLabelsArtefactid(){
    	Gettr thisGettr = new Gettr();    	
    	thisGettr.setqry(qrylblRoot+qrylblWhereArtefacts_IDs);
    	return thisGettr;
    }
    
    // labels by type, all
    @Path("/labels/all/type")
    public Gettr GettrLabelsType(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setqry(qrylblRoot+qrylblWhereArtefacts_Classification);
    	return thisGettr;
    }
    
    //labels by material, all
    @Path("/labels/all/material")
    public Gettr GettrLabelsMaterial(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setqry(qrylblRoot+qrylblWhereArtefacts_Material);
    	return thisGettr;
    }
    
    // labels by period, all
    @Path("/labels/all/period")
    public Gettr GettrLabelsPeriod(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setqry(qrylblRoot+qrylblWhereArtefacts_Period);
    	return thisGettr;
    }
    
    // labels by find location, all
    @Path("/labels/all/findlocation")
    public Gettr GettrLabelsFindLocation(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setqry(qrylblRoot+qrylblWhereArtefacts_FindLocation);
    	return thisGettr;
    }
    
    // labels by storage location, all
    @Path("/labels/all/museumlocation")
    public Gettr GettrLabelsMuseumLocation(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setqry(qrylblRoot+qrylblWhereArtefacts_StorageLocation);
    	return thisGettr;
    }
    
    // geo by ID, either as a string or URI
    @Path("/geo/id/{val}")
    public Gettr GettrGeoMon(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p87, true,qrygeoRoot,qrygeoWhereArtefacts);
    	return thisGettr;
    }
    
    // geo by type, either as a string or URI
    @Path("/geo/type/{val}")
    public Gettr GettrGeoType(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p2, false,qrygeoRoot,qrygeoWhereArtefacts);
    	return thisGettr;
    }
    
    // labels by ID, either as a string or URI
    @Path("/labels/id/{val}")
    public Gettr GettrLabelsArtefactid(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);    	
    	thisGettr.setqry(v,p1, true,qrylblRoot,qrylblWhereArtefacts_IDs);
    	return thisGettr;
    }
    
    // labels by type, either as a string or URI
    @Path("/labels/type/{val}")
    public Gettr GettrLabelsType(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p2, false,qrylblRoot,qrylblWhereArtefacts_Classification);
    	return thisGettr;
    }
    
    // geo by material, either as a string or URI
    @Path("/geo/material/{val}")
    public Gettr GettrGeoMaterial(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p45, false,qrygeoRoot,qrygeoWhereArtefacts);
    	return thisGettr;
    }
    
    // labels by material, either as a string or URI
    @Path("/labels/material/{val}")
    public Gettr GettrLabelsMaterial(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p45,false,qrylblRoot,qrylblWhereArtefacts_Material);
    	return thisGettr;
    }

    // geo by period, either as a string or URI
    @Path("/geo/period/{val}")
    public Gettr GettrGeoPeriod(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p92, false,qrygeoRoot,qrygeoWhereArtefacts);
    	return thisGettr;
    }

    // labels by period, either as a string or URI
    @Path("/labels/period/{val}")
    public Gettr GettrLabelsPeriod(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p92,false,qrylblRoot,qrylblWhereArtefacts_Period);
    	return thisGettr;
    }
    

    
}
