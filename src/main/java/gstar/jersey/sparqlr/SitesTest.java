package gstar.jersey.sparqlr;

public class SitesTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String context = "Feature";
		String contextuid = "FeatureID";
		String ctxtgeoid = "coords";
		String p87 = "crm:P87_is_identified_by";
		String p2 ="crm:P2_has_type";
		String p53 = "crm:P53_has_former_or_current_location";
		String e42 = "crm:E42_Identifier";
		String e27 = "crm:E27_Site";
		
		String v = "henge";
		String p =p2;
		Boolean u = true; 
		
		
		Sites s = new Sites();
		
		String qrygeo =
				"SELECT DISTINCT \n"+ 
				"?" + context + " ?gWKT ?" + contextuid + " \n"+
				"WHERE { \n"+
				"?" + context + " rdf:type " + e27 + " . \n"+ // added check for e27 to sort from e19 objects
				"?" + context + " " + p53 + " ?" + ctxtgeoid + ". \n"+
				"?" + ctxtgeoid + " geo:hasGeometry ?depiction . \n"+
				"?depiction geo:asWKT ?gWKT . \n"+
				"?" + context + " rdfs:label ?" + contextuid + " .";
		

		
		String qrylabels=
				"SELECT DISTINCT \n" +
				"?" + context + " ?label ?labeltype \n"+
				"WHERE { \n"+ 
				"?" + context + " rdf:type " + e27 + " . \n"+ // added check for e27 to sort from e19 objects
				"?" + context + " " + p87 + " ?id . \n"+
				"?id rdfs:label ?label . \n"+ 
				"?id a " + e42 + " . \n"+
				"?id " + p2 + " ?type . \n"+
				"?type rdfs:label ?labeltype . \n";

		
		//String outgeo = qrygeo + s.subSelect(s.parseparam(v,u),p);
		//String outlbl = qrylabels + s.subSelect(s.parseparam(v,u),p); 
		
		
		// test sparql tool
		SparqlTool spq = new SparqlTool();
		//spq.setQueryString(outgeo);
		
		String querystring = spq.getPrefixString() + "\n" + spq.getQueryString();
		
		System.out.println(querystring);
		System.out.println();
		
		
		
		// test gettr - results:
		
		/*
			Exception in thread "main" HttpException: 415
			at com.hp.hpl.jena.sparql.engine.http.HttpQuery.rewrap(HttpQuery.java:414)
			at com.hp.hpl.jena.sparql.engine.http.HttpQuery.execPost(HttpQuery.java:402)
			at com.hp.hpl.jena.sparql.engine.http.HttpQuery.exec(HttpQuery.java:294)
			at com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP.execResultSetInner(QueryEngineHTTP.java:346)
			at com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP.execSelect(QueryEngineHTTP.java:338)
			at gstar.jersey.sparqlr.SparqlTool.doSelectRS(SparqlTool.java:106)
			at gstar.jersey.sparqlr.Gettr.getModelSelect(Gettr.java:121)
			at gstar.jersey.sparqlr.Gettr.getIt(Gettr.java:47)
			at gstar.jersey.sparqlr.SitesTest.main(SitesTest.java:53)
		
		*/
		
		/*
		Gettr g = s.GettrGeoType("henge");
		String outgettr = g.getQueryString();
		String results = g.getIt("false");
		
		System.out.println(results);
		System.out.println();
		
		 */
		
		
		
		// test basic functions - fine
		/*
		System.out.println(outgeo);
		System.out.println();
		
		System.out.println(outlbl);
		System.out.println();
		
		System.out.println(outgettr);
		System.out.println();
		*/

	}

}
