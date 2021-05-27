var baseurl = 'http://localhost:8080/sparqlr/api/';
var geojsonurl_sites = baseurl + 'sites/geo/all/geojson';
var geojsonurl_artefacts = baseurl + 'artefacts/geo/all/geojson';
var archLayerGroupTitle = 'Archaeology Layers';
var queryLayerGroupTitle = 'Query Layers';


var ol3_GroupLayer_Query = new ol.layer.Group({
								'title': queryLayerGroupTitle,
								layers:[new ol.Collection()]
							});


// Extent of the map in units of the projection (these match our base map)
var extent = [-3276800, -3276800, 3276800, 3276800];

// Fixed resolutions to display the map at (pixels per ground unit (meters when
// the projection is British National Grid))
var resolutions = [1600,800,400,200,100,50,25,10,5,2.5,1,0.5,0.25,0.125,0.0625];

// Define British National Grid Proj4js projection (copied from http://epsg.io/27700.js)
proj4.defs("EPSG:27700","+proj=tmerc +lat_0=49 +lon_0=-2 +k=0.9996012717 +x_0=400000 +y_0=-100000 +ellps=airy +towgs84=446.448,-125.157,542.06,0.15,0.247,0.842,-20.489 +units=m +no_defs");

// Define an OL3 projection based on the included Proj4js projection
// definition and set it's extent.
var bng = ol.proj.get('EPSG:27700');
bng.setExtent(extent);

// Define a TileGrid to ensure that WMS requests are made for
// tiles at the correct resolutions and tile boundaries
var tileGrid = new ol.tilegrid.TileGrid({
    origin: extent.slice(0, 2),
    resolutions: resolutions
});

// mouse pos control
var mousePositionControl = new ol.control.MousePosition({
    coordinateFormat: ol.coordinate.createStringXY(4),
    projection: 'EPSG:27700',
    // comment the following two lines to have the mouse position
    // be placed within the map.
    //className: 'custom-mouse-position',
    //target: document.getElementById('mouse-position'),
    undefinedHTML: '&nbsp;'
});

// zoom to extent
var zoomExtents = new ol.control.ZoomToExtent({
		extent: [
        404000,135000,
        420000,150000
    ]
});	





// -- GeoJSON layer HER --

// Define a GeoJSON source that will load features via a http call. By
// specifying the projection of the map's view OL3 will transform the coordinates
// for display
		
var fSourceH = new ol.source.Vector({
		url: geojsonurl_sites,
		format: new ol.format.GeoJSON({
						defaultDataProjection: 'EPSG:27700'
						})
});


// Create a vector layer to display the features within the GeoJSON source and
// applies a simple icon style to all features
var fLayerH = new ol.layer.Vector({
	title: "Historic Environment Record",
	source: fSourceH,
	style: function(feature, resolution) {
	style.getText().setText(resolution < 1 ? feature.get('Feature') : '');
	return styles;
  }
});

// -- GeoJSON layer Museums --

// Define a GeoJSON source that will load features via a http call. By
// specifying the projection of the map's view OL3 will transform the coordinates
// for display
		
var fSourceM = new ol.source.Vector({
		url: geojsonurl_artefacts,
		format: new ol.format.GeoJSON({
						defaultDataProjection: 'EPSG:27700'
						})
});


// Create a vector layer to display the features within the GeoJSON source and
// applies a simple icon style to all features
var fLayerM = new ol.layer.Vector({
	title: 'Museum Artefacts',
	source: fSourceM,
	style: function(feature, resolution) {
	style.getText().setText(resolution < 1 ? feature.get('Feature') : '');
	return styles;
  }
});


	
// -- Map stuff --

var map = new ol.Map({
		target: 'map',  // The DOM element that will contains the map
		renderer: 'canvas', // Force the renderer to be used
		controls: ol.control.defaults({
			attributionOptions: /** @type {olx.control.AttributionOptions} */ ({
				collapsible: false
			})
		}).extend([mousePositionControl,zoomExtents]),
    
	layers: [
        new ol.layer.Group({
            'title': 'Base maps',
	
			layers: [
				new ol.layer.Tile({
					title: 'Ordnance Survey',
					source: new ol.source.TileWMS({
						url: 'http://t0.ads.astuntechnology.com/open/osopen/service?',
						attributions: [
							new ol.Attribution({html: 'Astun Data Service &copy; Ordnance Survey.'})
						],
						params: {
							'LAYERS': 'osopen',
							'FORMAT': 'image/png',
							'TILED': true
						},
						tileGrid: tileGrid
					})
				})
			]
		}),
		new ol.layer.Group({
			'title': archLayerGroupTitle,
			layers:[fLayerM,fLayerH]
		})	

	],
    view: new ol.View({
        projection: bng,
        resolutions: resolutions,
        center: [413674, 289141],
        zoom: 0
    })
});

// add a layer switcher
var layerSwitcher = new ol.control.LayerSwitcher({
	tipLabel: 'Layers' // Optional label for button
});
map.addControl(layerSwitcher);




// Once the change event of the source occurs and the source is 'ready' zoom to
// the extent of the features
//fSource.on('change', function (evt) {
//	var src = evt.target;
//	if (src.getState() === 'ready') {
//		map.getView().fitExtent(src.getExtent(), map.getSize());
//	}
//});


// -- Display information on singleclick --

// Create a popup overlay which will be used to display feature info
var element = document.getElementById('popup');

var popup = new ol.Overlay({
	element: element,
	positioning: 'bottom-center',
	stopEvent: false
});
map.addOverlay(popup);



// Add an event handler for the map "click" event
map.on('click', function(evt) {
    // Attempt to find a feature in one of the visible vector layers
    var feature = map.forEachFeatureAtPixel(evt.pixel, 
		function(feature) {
			return feature;
		});
    if (feature) {
		popup.setPosition(evt.coordinate);
		$(element).popover({
		'placement': 'top',
		'html': true,
		'content': feature.get('FeatureID')
		});
		$(element).popover('show');
    } 
	else {
        $(element).popover('destroy');
    }

});

//Styles and style cache
var styleCache = {};
var fill = new ol.style.Fill({
	color: [250,250,250,1]
});
var stroke = new ol.style.Stroke({
	color: [220,220,220,1],
	width: 1
});
var defaultStyle = new ol.style.Style({
	fill: fill,
	stroke: stroke,
	image: new ol.style.Circle({
		fill: fill,
		stroke: stroke,
		radius: 5
	})
});



