package gstar.jersey.sparqlr;

import com.hp.hpl.jena.sparql.resultset.ResultsFormat;

public class GettrTest  {
	
	
	
	//api settings
	public String delims = "[~]+";
	
	// constants used in sparql queries
	// results names
	public static String context = "Feature";
	public static String contextuid = "FeatureID";
	public static String ctxtgeoid = "coords";
	public static String filt = "class";
	
	// crm entities
	public static String e19 = "crm:E19_Physical_Object";
	public static String e27 = "crm:E27_Site";
	public static String e41 = "crm:E41_Appellation";
	public static String e42 = "crm:E42_Identifier";
	
	//crm properties
	public static String p1 = "crm:P1_is_identified_by";
	public static String p2 ="crm:P2_has_type";
	public static String p10 = "crm:P10_falls_within";
	public static String p45 ="crm:P45_consists_of";
	public static String p53 = "crm:P53_has_former_or_current_location";
	public static String p87 = "crm:P87_is_identified_by";
	public static String p92 = "crm:P92i_was_brought_into_existence_by"; // inverse - looks to be a loading error :-(
	
	// gstar uris
	public static String montype = "gstar:E55_MonType";
	public static String monperiod = "gstar:E55_MonPeriod";
	public static String vocabtype = "gstar:E55_Vocabulary";
	public static String artefactperiod = "gstar:E55_ObjectPeriod";
	public static String artefacttype = "<http://ld.gstar.archaeogeomancy.net/content/E55_Object+Classification>";
	public static String artefactmaterial = "<http://ld.gstar.archaeogeomancy.net/content/E55_Object+Material>";
	
	// query string params
	public static String qrygeoRoot = 
			"SELECT DISTINCT \n"+ 
			"?" + context + " ?gWKT ?" + contextuid + " ?" + filt + " \n"+
			"WHERE { \n";
	
	public static String qrylblRoot = 
			"SELECT DISTINCT \n" +
			"?" + context + " ?label ?labeltype ?" + filt + " \n"+
			"WHERE { \n";
	
	public static String qrygeoWhereSites = 
			"?" + context + " rdf:type " + e27 + " . \n"+ // added check for e27 to sort from e19 objects
			"?" + context + " " + p53 + " ?" + ctxtgeoid + ". \n"+
			"?" + ctxtgeoid + " geo:hasGeometry ?depiction . \n"+
			"?depiction geo:asWKT ?gWKT . \n"+
			"?" + context + " rdfs:label ?" + contextuid + " .";
	
	public static String qrygeoWhereArtefacts = 
			"?" + context + " rdf:type " + e19 + " . \n"+ // added check for e19 to sort from e27 sites
			"?" + context + " " + p53 + " ?" + ctxtgeoid + ". \n"+
			"?" + ctxtgeoid + " geo:hasGeometry ?depiction . \n"+
			"?depiction geo:asWKT ?gWKT . \n"+
			"?" + context + " rdfs:label ?" + contextuid + " .";
	
	public static String qrylblWhereSites = 
			"?" + context + " rdf:type " + e27 + " . \n"+ // added check for e27 to sort from e19 objects
			"?" + context + " " + p87 + " ?id . \n"+
			"?id rdfs:label ?label . \n"+ 
			"?id a " + e42 + " . \n"+
			"?id " + p2 + " ?type . \n"+
			"?type rdfs:label ?labeltype . \n";
	
	public static String qrylblWhereArtefacts = 
			"?" + context + " rdf:type " + e19 + " . \n"+ // added check for e19 to sort from e27 sites
			"?" + context + " " + p1 + " ?id . \n"+
			"?id rdfs:label ?label . \n"+ 
			"?id a " + e41 + " . \n"; // TODO: add extra triples to explicitly state E42s 
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gettr g = new Gettr(true);
		String v="http%3A%2F%2Fld.gstar.archaeogeomancy.net%2Fcontent%2FE55_ARMY%2BCAMP";
		g.setqry(v,p2,true,qrygeoRoot,qrygeoWhereSites);
		String qrystring = g.getQueryString();
		String out = g.getModelSelect(ResultsFormat.FMT_RS_JSON,qrystring);
		System.out.println("Query string:");
		System.out.println(g.getQueryString());
		System.out.println("Results as json:");
		System.out.println(out);
		System.out.println(g.getDebugmsg());
		
		
		

	}

}
