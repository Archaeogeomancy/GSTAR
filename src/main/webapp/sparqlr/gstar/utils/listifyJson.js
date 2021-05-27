function tablifyJson(divid, jsonurl){
	// create a list

	var theList = $("<ul></ul>").addClass("infobox");

	$.getJSON(jsonurl, function(data) {  	

		// get the sparql variables from the 'head' of the data.
		var headerVars = data.head.vars; 
		
		// using the vars, make some table headers and add them to the table;
		var trHeaders = getTableHeaders(headerVars);
		//alert("headers set");
		table.append(trHeaders);  
		
		// grab the actual results from the data.                                          
		var bindings = data.results.bindings;
		
		// for each result, make a table row and add it to the table.
		$.each(bindings, function(index,value) {{
			var li = $("<li></li>");
		  theList.append(getTableRow(headerVars, bindings[rowIdx]));
		} 
	
	});