(function($) { //start of main jQuery closure
	$(window).load(function(){
		
		// alert("sen running");
		

		
		//SENESCHAL 

		// element configured to suggest terms from Monument Types (EH) thesaurus
		$("#suggestMonType").termsuggest({
			schemeURI: "http://purl.org/heritagedata/schemes/eh_tmt2"
		});
		
		// #myConceptDetailsComposite element will become a conceptdetailscomposite widget
		// and initially display details for a specified concept URI
		$("#myConceptDetailsComposite").conceptdetailscomposite();

		// taking some action when a concept is selected from ANY of the termsuggest controls
		$(".usw-seneschal-termsuggest").termsuggest().bind("selected", function(e, data) {
			// build an html string containing the selected data
			var s = "<p>You selected '" + data.label + "'<br>";
			s+= "The identifier is <a href='" + data.uri + "' target='_blank'>" + data.uri + "</a></p>";
			// display the built string in the #mySelection element
			$("#mySelection").html(s);
			
			// make the conceptdetailscomposite display details of the selected concept
			$("#myConceptDetailsComposite").conceptdetailscomposite({"conceptURI": data.uri, "conceptLabel": data.label});
		});
		
		
		// bit for the concept details:
		

		
		// taking some action when a concept is selected from the list
		$("#myConceptDetailsComposite").conceptdetailscomposite().bind("selected", function(e, data) {
			// build an html string containing the selected data
			var s = "You selected '" + data.label + "'<br>";
			s+= "The identifier is <a href='" + data.uri + "' target='_blank'>" + data.uri + "</a>";
			// display the built string in the #mySelection element
			$("#mySelection").html(s);
			
			// blank the term suggest control
			$(function () {
				$('#suggestMonType').val("");
			});
		});
		

		

	}); // end window load
})(jQuery); //end of main jQuery closure

