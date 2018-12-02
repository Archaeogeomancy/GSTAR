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
@Path("sites")
public class Sites extends ApiThing {

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
    
    // geo by id, all 
    @Path("/geo/all")
    public Gettr GettrGeo(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setqry(qrygeoRoot + qrygeoWhereSites);
    	return thisGettr;
    }
    
    // labels by id, all 
    @Path("/labels/all")
    public Gettr GettrLabels(){
    	Gettr thisGettr = new Gettr();
    	thisGettr.setqry(qrylblRoot + qrylblWhereSites);
    	return thisGettr;
    }
    
    /* URL path params
     * incoming named 'val'
     * passed to query string function as 'v'
     */
    
    // geo by monuid, either as a string or URI
    @Path("/geo/id/{val}")
    public Gettr GettrGeoMon(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p87,true,qrygeoRoot,qrygeoWhereSites);
    	return thisGettr;
    }
    
    // geo by type, either as a string or URI
    @Path("/geo/type/{val}")
    public Gettr GettrGeoType(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p2,true,qrygeoRoot,qrygeoWhereSites);
    	return thisGettr;
    }
    
    // labels by monuid, either as a string or URI
    @Path("/labels/id/{val}")
    public Gettr GettrLabelsMonuid(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p87,true,qrylblRoot,qrylblWhereSites);
    	return thisGettr;
    }

    // labels by prefref, either as a string or URI
    @Path("/labels/prefref/{val}")
    public Gettr GettrLabelsMonPrefRef(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p87,true,qrylblRoot,qrylblWhereSites);
    	return thisGettr;
    }
    
    // labels by type, either as a string or URI
    @Path("/labels/type/{val}")
    public Gettr GettrLabelsType(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p2, true,qrylblRoot,qrylblWhereSites);
    	return thisGettr;
    } 
    
    // geo by period, either as a string or URI
    @Path("/geo/period/{val}")
    public Gettr GettrGeoPeriod(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p92, false,qrygeoRoot,qrygeoWhereSites);
    	return thisGettr;
    }
    
    // labels by period, either as a string or URI
    @Path("/labels/period/{val}")
    public Gettr GettrLabelsPeriod(@DefaultValue("}") @PathParam("val") String v, @DefaultValue("false") @QueryParam("uri") boolean b){
    	Gettr thisGettr = new Gettr(b);
    	thisGettr.setqry(v,p92,false,qrylblRoot,qrylblWhereSites);
    	return thisGettr;
    }

}
