
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
function populate(arr, cntrl) {
    var i;
    $(cntrl).html("");
    for(i = 0; i < arr.length; i++) {
        $(cntrl).append("<option value='"+arr[i].vocabterm.value+"'>"+arr[i].vocabterm.value+" ("+ arr[i].occurence.value+")</option>");
    }
    
    
}


getVocab("http://localhost:8080/sparqlr/api/vocab/list/json","#dropdown_Vocabularies")

function setSubSelect(vocab, dropdown){
	var vocaburl = "http://localhost:8080/sparqlr/api/vocab/url/" + encodeURI(vocab) + "/json";
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var obj = JSON.parse(xmlhttp.responseText);
			var arr = obj.results.bindings;
			var thisurl = arr[0].jsonurl.value;
			getVocab(thisurl,dropdown)
			
		}
	}
	xmlhttp.open("GET", vocaburl, true);
	xmlhttp.send();

	
	
	
}



