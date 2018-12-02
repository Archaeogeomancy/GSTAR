package gstar.jersey.sparqlr;

public class ApiThing {
	
	//api settings
	public String delims = "[~]";
	
	// constants used in sparql queries
	// results names
	public String context = "Feature";
	public String contextuid = "FeatureID";
	public String ctxtgeoid = "coords";
	public String filt = "val";
	
	// crm entities
	public String e19 = "crm:E19_Physical_Object";
	public String e27 = "crm:E27_Site";
	public String e41 = "crm:E41_Appellation";
	public String e42 = "crm:E42_Identifier";
	
	//crm properties
	public String p1 = "crm:P1_is_identified_by";
	public String p2 ="crm:P2_has_type";
	public String p10 = "crm:P10_falls_within";
	public String p45 ="crm:P45_consists_of";
	public String p53 = "crm:P53_has_former_or_current_location";
	public String p54 = "crm:P54_has_current_permanent_location";
	public String p87 = "crm:P87_is_identified_by";
	public String p89 = "crm:P89_falls_within";
	public String p92 = "crm:P92i_was_brought_into_existence_by"; // inverse - looks to be a loading error :-(
		
	
	// gstar uris
	public String montype = "gstar:E55_MonType";
	public String monperiod = "gstar:E55_MonPeriod";
	public String vocabtype = "gstar:E55_Vocabulary";
	public String artefactperiod = "gstar:E55_ObjectPeriod";
	public String artefact = "<http://ld.gstar.archaeogeomancy.net/content/E55_Museum+Object>";
	public String artefacttype = "<http://ld.gstar.archaeogeomancy.net/content/E55_Object+Classification>";
	public String artefactmaterial = "<http://ld.gstar.archaeogeomancy.net/content/E55_Object+Material>";
	public String findlocation = "gstar:E55_FindLocation";
	public String museum = "gstar:E53_Museum";
	
	// query string params for GEO queries
	public String qrygeoRoot = 
			"SELECT DISTINCT \n"+ 
			"?" + context + " ?gWKT ?" + contextuid + " ?" + filt + " \n"+
			"WHERE { \n";

	public String qrygeoWhereSites = 
			"?" + context + " rdf:type " + e27 + " . \n"+ // added check for e27 to sort from e19 objects
			"?" + context + " " + p53 + " ?" + ctxtgeoid + ". \n"+
			"?" + ctxtgeoid + " geo:hasGeometry ?depiction . \n"+
			"?depiction geo:asWKT ?gWKT . \n"+
			"?" + context + " rdfs:label ?" + contextuid + " .";
	
	public String qrygeoWhereArtefacts = 
			"?" + context + " rdf:type " + e19 + " . \n"+ // added check for e19 to sort from e27 sites
			"?" + context + " " + p53 + " ?" + ctxtgeoid + ". \n"+
			"?" + ctxtgeoid + " geo:hasGeometry ?depiction . \n"+
			"?depiction geo:asWKT ?gWKT . \n"+
			"?" + context + " rdfs:label ?" + contextuid + " .";
	
	
	
	// query string params for LBL queries
	
	/*
	public String qrylblRoot = 
			"SELECT DISTINCT \n" +
			"?" + context + " ?label ?labeltype ?" + filt + " \n"+
			"WHERE { \n";
	
	public String qrylblWhereSites = 
			"?" + context + " rdf:type " + e27 + " . \n"+ // added check for e27 to sort from e19 objects
			"?" + context + " " + p87 + " ?id . \n"+
			"?id rdfs:label ?label . \n"+ 
			"?id a " + e42 + " . \n"+
			"?id " + p2 + " ?type . \n"+
			"?type rdfs:label ?labeltype . \n";
			
	*/
	//update: now using UNION + key/val pairs
	
	public String qrylblRoot = 
			"SELECT DISTINCT \n" +
			"?" + context + " ?" + contextuid + " ?key ?val \n"+
			"WHERE { \n";
	
	public String qrylblWhereSites = ""+
		"?" + context + " a crm:E27_Site . \n "+
		"?" + context + " rdfs:label ?" + contextuid + " . "+
		"{ \n "+
		"	?" + context + " " + p2 + " ?t . \n "+
		"	?t rdfs:label ?val . \n "+
		"	?t " + p2 + " gstar:E55_MonType . \n "+
		"	gstar:E55_MonType rdfs:label ?key \n "+
		"} \n "+
		"UNION { \n "+
		"	?" + context + " " + p92 + " ?creation . \n "+
		"	?creation " + p10 + " ?perev . \n "+
		"	?perev rdfs:label ?val . \n "+
		"	?perev skos:inScheme ?scheme . \n "+
		"	?scheme rdfs:label ?key . \n "+
		"} \n "+
		"UNION { \n "+
		"?" + context + " " + p1 + " ?id . \n "+
		"	?id rdfs:label ?val . \n "+
		"	?id " + p2 + " ?idtype . \n "+
		"	?idtype rdfs:label ?key . \n "+
		"} \n ";
	
	
	
	public String qrylblWhereArtefacts_IDs =
		"?" + context + " " + p2 + " "+ artefact + " . \n"+
		"?" + context + " rdfs:label ?" + contextuid + " . "+
		"?" + context + " " + p1 + " ?id . \n"+
		"?id rdfs:label ?val . \n"+
		"BIND('Identifier'@en AS ?key) . \n";
	
	public String qrylblWhereArtefacts_FindLocation =
		"?" + context + " " + p2 + " "+ artefact + " . \n"+
		"?" + context + " rdfs:label ?" + contextuid + " . "+
		"?" + context + " " + p53 + " ?findloc . \n"+
		"?findloc " + p2 + " " + findlocation + "; \n"+
			p89 + " ?place . \n"+
		"?place rdfs:label ?val ; \n"+
			p2 + "/rdfs:label ?key . \n";

	public String qrylblWhereArtefacts_StorageLocation =
		"?" + context + " " + p2 + " " + artefact + " . \n"+
		"?" + context + " rdfs:label ?" + contextuid + " . "+
		"?" + context +  " " + p54 + " ?museum . \n"+
		"?museum a " + museum + " ; \n"+
			"rdfs:label ?val . \n"+
		museum + " rdfs:label ?key . \n";
	
	public String qrylblWhereArtefacts_Classification =
		"?" + context + " " + p2 + " " + artefact + " . \n"+
		"?" + context + " rdfs:label ?" + contextuid + " . "+
		"?" + context + " " + p2 + " ?type . \n"+
		"?type " + p2 + " " + artefacttype + " ; \n"+
			"rdfs:label ?val . \n"+
		artefacttype + " rdfs:label ?key . \n";
		
	public String qrylblWhereArtefacts_Material =
		"?" + context + " " + p2 + " " + artefact + " . \n"+
		"?" + context + " rdfs:label ?" + contextuid + " . "+
		"?" + context + " " + p45 + " ?material . \n"+
		"?material " + p2 + " " + artefactmaterial + " ; \n"+
			"rdfs:label ?val . \n"+
		artefactmaterial + " rdfs:label ?key . \n";
		
	public String qrylblWhereArtefacts_Period =
		"?" + context + " " + p2 + " " + artefact + " . \n"+
		"?" + context + " rdfs:label ?" + contextuid + " . "+
		"?" + context + " " + p92 + "/" + p10 + " ?perev . \n"+
		"?perev " + p2 + " " + artefactperiod + " ; \n"+ 
			"rdfs:label ?val . \n"+
		artefactperiod + " rdfs:label ?key . \n";
}
