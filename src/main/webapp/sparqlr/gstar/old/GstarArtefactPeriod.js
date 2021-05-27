/*
	based on http://dailyjs.com/2010/11/26/linked-data-and-javascript/
	recoded to use $.each() using http://api.jquery.com/jquery.each/
*/
$(document).ready(function(){
	var divid = "#vocabTbl";
	var jsonurl = "http://localhost:8080/sparqlr/api/vocab/artefact/periods/json";
	tablifyJson(divid, jsonurl);
	
}); 
