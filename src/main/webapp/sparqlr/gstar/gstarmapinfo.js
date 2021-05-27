var style = new ol.style.Style({
  fill: new ol.style.Fill({
    color: 'rgba(255, 255, 255, 0.6)'
  }),
  stroke: new ol.style.Stroke({
    color: '#319FD3',
    width: 1
  }),
  text: new ol.style.Text({
    font: '12px Calibri,sans-serif',
    fill: new ol.style.Fill({
      color: '#000'
    }),
    stroke: new ol.style.Stroke({
      color: '#fff',
      width: 3
    })
  })
});
var styles = [style];




var highlightStyleCache = {};

var featureOverlay = new ol.layer.Vector({
  source: new ol.source.Vector(),
  map: map,
  style: function(feature, resolution) {
    var text = resolution < 5000 ? feature.get('Feature') : '';
    if (!highlightStyleCache[text]) {
      highlightStyleCache[text] = [new ol.style.Style({
        stroke: new ol.style.Stroke({
          color: '#f00',
          width: 1
        }),
        fill: new ol.style.Fill({
          color: 'rgba(255,0,0,0.1)'
        }),

      })];
    }
    return highlightStyleCache[text];
  }
});

var highlight;
var displayFeatureInfo = function(pixel) {

  var feature = map.forEachFeatureAtPixel(pixel, function(feature, layer) {
    return feature;
  });

  var info = document.getElementById('info');
  if (feature) {
	//info.innerHTML = feature.get('Feature');
	//alert(JSON.stringify(selectLabels(feature.get('Feature'))));
	//var thishtml = htmlifyLabels(selectLabels(feature.get('Feature')));
	//info.innerHTML = htmlifyLabels(selectLabels(feature.get('Feature')));
	
	var thishtml = "<table>";
	var jsonarray = selectLabels(feature.get('Feature'))
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
    info.innerHTML = thishtml;
  } else {
    info.innerHTML = '&nbsp;';
  }

  if (feature !== highlight) {
    if (highlight) {
      featureOverlay.getSource().removeFeature(highlight);
    }
    if (feature) {
      featureOverlay.getSource().addFeature(feature);
    }
    highlight = feature;
  }

};

map.on('pointermove', function(evt) {
  if (evt.dragging) {
    return;
  }
  var pixel = map.getEventPixel(evt.originalEvent);
  displayFeatureInfo(pixel);
});

map.on('click', function(evt) {
  displayFeatureInfo(evt.pixel);
});

