function tablifyJson(divid, jsonurl){
	// create a table 
	var table = $("<table id='tablify'></table>").addClass("sortable");

	$.getJSON(jsonurl, function(data) {  
					
		// get the table element
		//var table = $(tableid);              

		// get the sparql variables from the 'head' of the data.
		var headerVars = data.head.vars; 
		
		// using the vars, make some table headers and add them to the table;
		var trHeaders = getTableHeaders(headerVars);
		//alert("headers set");
		table.append(trHeaders);  
		
		// grab the actual results from the data.                                          
		var bindings = data.results.bindings;
		
		// for each result, make a table row and add it to the table.
		for(rowIdx in bindings){
		  table.append(getTableRow(headerVars, bindings[rowIdx]));
		} 
	
	});
	
	//append table
	$(divid).append(table);
	sorttable.makeSortable($("tablify"));
	
	function getTableHeaders(h) {
		var trHeaders = $("<tr></tr>");
		$.each(h, function(index, value) {
			//console.log("Header Value: " + value);
			trHeaders.append( $("<th>" + value + "</th>") );
		});
		return trHeaders;
	}

	function getTableRow(h, r) {
		var tr = $("<tr></tr>");
		$.each(h, function(index,value) {
			tr.append(getTableCell(value, r));			
		});
		return tr;
	}

	function getTableCell(f, r) {
		//alert(f + ": " + r);
		var td = $("<td></td>");
		var fieldData = r[f];
		//console.log("Field Value: " + fieldData["value"]);
		td.html(fieldData["value"]);
		return td;
	}	
}