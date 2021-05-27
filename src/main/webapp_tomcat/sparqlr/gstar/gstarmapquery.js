
var baseurl = 'http://localhost:8080/sparqlr/api/';
var delimurl = "~";
var featurecolourvariable = 'display';
var featureclassvariable = 'class';

// gets vocab terms and populates a selector
function getVocab(jsonurl,dropdown){
	$(dropdown).html('');
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var obj = JSON.parse(xmlhttp.responseText);
			populate(obj.results.bindings, dropdown);
		}
	}
	xmlhttp.open("GET", jsonurl, true);
	xmlhttp.send();
}
// populates a given selector with values
function populate(arr, cntrl) {
	var i;
	$(cntrl).html("");
	for(i = 0; i < arr.length; i++) {
		//original using terms
		// $(cntrl).append("<option value='"+ arr[i].vocabterm.value +"'>"+arr[i].vocabterm.value+" ("+ arr[i].occurence.value+")</option>"); 
		//redesign using encoded uris
		$(cntrl).append("<option value='"+ arr[i].vocabterm.value + "#" + arr[i].classification.value +"'>"+arr[i].vocabterm.value+" ("+ arr[i].occurence.value+")</option>"); 
	}
	$(cntrl).multiselect({title: "Select Term"});
	
	
}

// sets values for the selectors
function setSelect(vocab, dropdown){
	var vocaburl = baseurl + "vocab/url/" + encodeURI(vocab) + "/json";
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var obj = JSON.parse(xmlhttp.responseText);
			var arr = obj.results.bindings;
			var thisurl = arr[0].jsonurl.value;
			getVocab(thisurl,dropdown);
			
		}
	}
	xmlhttp.open("GET", vocaburl, true);
	xmlhttp.send();

}




// adds related terms for given thes term by selecting values in a given selector
function addRelated(term, dropdown){
	var vocaburl = baseurl + "vocab/skos/related/" + encodeURI(term) + "/json";
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var obj = JSON.parse(xmlhttp.responseText);
			var arr = obj.results.bindings;

			for(i = 0; i < arr.length; i++) {
				var thisTerm = arr[i].term.value;
				// check to see if value exists before selecting it
				$(dropdown + ' option').each(function(){
					//alert(this.value);
					var exists = $(dropdown + ' option').filter(function(){ 
						return $(this).val() == thisTerm; 
					}).length;
					if (exists) {
						$(dropdown).multiselect("selectOption", "'" + thisTerm + "'");
					}
				});
			}
		}
	}
	xmlhttp.open("GET", vocaburl, true);
	xmlhttp.send();
}


// get data for the info tools
var alllabelsarray =[];

var jsonurl_labels_sites_all = baseurl + "sites/labels/all/json";
var jsonurl_labels_artefacts_id = baseurl + "artefacts/labels/all/id/json";
var jsonurl_labels_artefacts_type = baseurl + "artefacts/labels/all/type/json";
var jsonurl_labels_artefacts_material = baseurl + "artefacts/labels/all/material/json";
var jsonurl_labels_artefacts_period = baseurl + "artefacts/labels/all/period/json";
var jsonurl_labels_artefacts_findlocation = baseurl + "artefacts/labels/all/findlocation/json";
var jsonurl_labels_artefacts_museum = baseurl + "artefacts/labels/all/museumlocation/json";

function getLabelsArray(jsonurl){
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var obj = JSON.parse(xmlhttp.responseText);
			var results = obj.results.bindings;
			alllabelsarray = alllabelsarray.concat(results);
		}
	}
	xmlhttp.open("GET", jsonurl, true);
	xmlhttp.send();
	
	
}
//returns a subset of the label array based on the uri provided, stored in the array as 'Feature'
function selectLabels(selectval){
	var matches = $.grep(alllabelsarray, function(e) {
		return e.Feature.value == selectval
	});
	return matches;
}

function htmlifyLabels(jsonarray){
	var thishtml = "<table>";
	for (var i in jsonarray){
		//alert(jsonarray[i].key.value);
		//alert("htmlify this: " + JSON.stringify(jsonarray));
		//alert("key: " + JSON.stringify(jsonarray[i].key));
		var thisKey = jsonarray[i].key.value;
		var thisVal = jsonarray[i].val.value;
		//alert(thisKey + " : " + thisVal);
		
		//if (thisKey == "Online Reference"){
			thishtml = thishtml + "<tr><td>" + thisKey + "</td><td><a href='" + thisVal + "'>" + thisVal + "</a></td></tr>";
		//}
		//else {
		//	thishtml = thishtml + "<tr><td>" + thisKey + "</td><td>" + thisVal + "</td></tr>";
		//}
		
	}
	thishtml = thishtml+"</table>";
	alert(thishtml);
	return thishtml;
}



// build layer properties based on button ID
// IDs: 
// 1. montypemapbtn
// 2. monperiodmapbtn
// 3. objtypemapbtn
// 4. objperiodmapbtn
// 5. objmaterialmapbtn

// builds a layer name for a map layer 
function buildLayerName(id){
	var layername = "";
	switch (id){
		case "montypemapbtn":
			layername = "Monuments by Type: " + buildParamString(id,", ",false,0);
			return layername;
		case "monperiodmapbtn":
			layername = "Monuments by Period: " + buildParamString(id,", ",false,0);
			return layername;
		case "objtypemapbtn":
			layername = "Objects by Type: " + buildParamString(id,", ",false,0);
			return layername;
		case "objperiodmapbtn":
			layername = "Objects by Period: " + buildParamString(id,", ",false,0);
			return layername;
		case "objmaterialmapbtn":
			layername = "Objects by Material: " + buildParamString(id,", ",false,0);
			return layername;
	}		
}
// builds a json url for a map layer 
function buildLayerUrl(id){
	var jsonurl = "";
	switch (id){
		case "montypemapbtn":
			jsonurl = baseurl + "sites/geo/type/" + buildParamString(id,delimurl,true,1) + "/geojson?uri=true";
			return jsonurl;
		case "monperiodmapbtn":
			jsonurl = baseurl + "sites/geo/period/" + buildParamString(id,delimurl,true,1) + "/geojson?uri=true";
			return jsonurl;
		case "objtypemapbtn":
			jsonurl = baseurl + "artefacts/geo/type/" + buildParamString(id,delimurl,true,1) + "/geojson?uri=true";
			return jsonurl;
		case "objperiodmapbtn":
			jsonurl = baseurl + "artefacts/geo/period/" + buildParamString(id,delimurl,true,1) + "/geojson?uri=true";
			return jsonurl;
		case "objmaterialmapbtn":
			jsonurl = baseurl + "artefacts/geo/material/" + buildParamString(id,delimurl,true,1) + "/geojson?uri=true";
			return jsonurl;
	}	
}
// builds filter sting to be used for layer name and json url
// uses 1st element (0) in option value for term; 2nd element (1) in option value for uri
function buildParamString(btn, delim, encode,thisValue){
	var selectedValues = getValues(btn);
	var filt = 	"";
	for (index = 0, len = selectedValues.length; index < len; ++index) {
		thisValuePair = selectedValues[index].split("#");
		if (encode){
			filt = filt + encodeURIComponent(thisValuePair[thisValue].trim());
		}
		else {
			filt = filt + thisValuePair[thisValue].trim();
		}
		//add a delimiter if more to come
		if ((index+1)<len){
			filt = filt + delim;
		}
	}
	return filt;
}

//gets the array of selected terms from the selector
function getValues(btn){
	var sel = btn.substring(0,(btn.length - 6));
	sel = "#" + sel;
	var selectedValues = $(sel).val();
	return selectedValues;
}

/*
// http://www.acuriousanimal.com/thebookofopenlayers3/chapter04_03_styling.html

var customStyleFunction = function(feature, resolution) {

	var fontSize = '18';
	if(resolution>=39134) {
		fontSize = '10';
	} else if(resolution>=9782) {
		fontSize = '14';
	} else if(resolution>=2444) {
		fontSize = '16';
	}

	return [new ol.style.Style({
		text: new ol.style.Text({
			font: fontSize + 'px sans-serif,helvetica',
			text: feature.get('CITY_NAME'),
			fill: new ol.style.Fill({
				color: '#'+Math.floor(Math.random()*16777215).toString(16)
			}),
			stroke: new ol.style.Stroke({
				color: '#ddd',
				width: 1
			})
		})
	})];
}; 
*/


// -- Styling of query layers --

function styleFunction(feature, resolution) {
	// get the incomeLevel from the feature properties
	var fclass = feature.get(featureclassvariable);
	// if there is no level or its one we don't recognize,
	// return the default style (in an array!)
	if (!fclass) {
		return [defaultStyle];
	}
	// check the cache and create a new style for the income
	// level if its not been created before.
	if (!styleCache[fclass]) {
		var col = '#'+Math.floor(Math.random()*16777215).toString(16);
		var classfill = new ol.style.Fill({
			color: col
		});
		var classstroke = new ol.style.Stroke({
			color: col,
			width: 1.25
		});
		styleCache[fclass] = new ol.style.Style({
			image: new ol.style.Circle({
				fill: classfill,
				stroke: classstroke,
				radius: 5
			}),
			fill: classfill,
			stroke: classstroke
		});
	}
	// at this point, the style for the current level is in the cache
	// so return it (as an array!)
	return [styleCache[fclass]];
}

function getStyle(fclass){
	return styleCache[fclass];
}


// build query layer and add to map
function addQueryLayerToMap(jsonurl, layername, values){
	//colours = addLyrCols(values);
	var fSource = new ol.source.Vector({
		url: jsonurl,
		format: new ol.format.GeoJSON({
			defaultDataProjection: 'EPSG:27700'
		})
	});
	
	var fLayer = new ol.layer.Vector({
		title: layername,
		source: fSource,
		style: styleFunction
	});
	map.addLayer(fLayer);

	
}

function buildLegend(layername,classvalues){
	var legenditem = "";
	alert(JSON.stringify(styleCache));
	for (index = 0, len = classvalues.length; index < len; ++index) {
		var style = getStyle(classvalues[index]);
		var $div = $("<div>", {id: "foo", class: "a"});
		legenditem = legenditem + "<p>" + classvalues[index] + " - " + JSON.stringify(style) +"</p>";
		}
	$("#maplegends").append(legenditem);
}

$(function() {
	$('#svgbasics').svg();
});
function drawInitial(svg) {
	svg.circle(75, 75, 50, {fill: 'none', stroke: 'red', strokeWidth: 3});
	var g = svg.group({stroke: 'black', strokeWidth: 2});
	svg.line(g, 15, 75, 135, 75);
	svg.line(g, 75, 15, 75, 135);
}



(function($) { //start of main jQuery closure

	var thesTerm;
	$(document).ready(function () {
		//alert(baseurl);
		// button to build map layer
		$(document).on('click', ".add2map", function (e) {
			alert("map button clicked");
			e.preventDefault();
			id = $(this).attr('id');
			var layerurl = buildLayerUrl(id);
			var layername = buildLayerName(id);
			var classvalues = getValues(id);

			addQueryLayerToMap(layerurl,layername,classvalues);
			buildLegend(layername,classvalues);

		});
		
		//button to populate selector with terms
		$(document).on('click', ".addterms", function (e) {
			
			e.preventDefault();
			id = $(this).attr('id');
			var sel = id.substring(0,(id.length - 6));
			sel = "#" + sel;
			addRelated(thesTerm,sel);
			//alert(thesTerm);
		});
				
	});

	$(window).load(function(){
		
		//populate selectors
		setSelect("Monument Type Classification","#montype")
		setSelect("Monument Period","#monperiod")
		setSelect("Object Classification","#objtype")
		setSelect("Object Material","#objmaterial")
		setSelect("Object Period","#objperiod")
		
		//populate label arrays
		getLabelsArray(jsonurl_labels_sites_all);
		getLabelsArray(jsonurl_labels_artefacts_id);
		getLabelsArray(jsonurl_labels_artefacts_type);
		getLabelsArray(jsonurl_labels_artefacts_material);
		getLabelsArray(jsonurl_labels_artefacts_period);
		getLabelsArray(jsonurl_labels_artefacts_findlocation);
		getLabelsArray(jsonurl_labels_artefacts_museum);
		

		
		//SENESCHAL 

		// #myTermSearch elements will become a termsearch widget
		$("#myTermSearch").termsearch();
		$("#myTermSearch").termsearch({schemeURI: "http://purl.org/heritagedata/schemes/eh_period"});

		
		// #myConceptDetailsComposite element will become a conceptdetailscomposite widget
		// and initially display details for a specified concept URI
		$("#myConceptDetailsCompositeO").conceptdetailscomposite();
		$("#myConceptDetailsCompositeO").conceptdetailscomposite({conceptURI: "http://purl.org/heritagedata/schemes/eh_period/concepts/UN"});
	

		// taking some action when a concept is selected from ANY of the termsuggest controls
		$(".usw-seneschal-termsearch").termsearch().bind("selected", function(e, data) {
			thesTerm = data.label;
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
			thesTerm = data.label;
			// build an html string containing the selected data
			var s = "You selected '" + data.label + "'<br>";
			s+= "The identifier is <a href='" + data.uri + "' target='_blank'>" + data.uri + "</a>";
			// display the built string in the #mySelection element
			$("#mySelection").html(s);
			
		});

	}); // end window load
})(jQuery); //end of main jQuery closure














