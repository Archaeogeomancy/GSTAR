<!doctype html>
<html lang="en">
  <head>
    <link rel="stylesheet" href="ol.css" type="text/css">
    <style>
      .map {
        height: 400px;
        width: 100%;
      }
    </style>
    <script src="ol.js" type="text/javascript"></script>
    <script src="openspace-ol3.js" type="text/javascript"></script>
    <script type="text/javascript" src="geojson.min.js"></script>
    <title>OpenLayers 3 example</title>
  </head>
  <body>
    <h2>My Map</h2>
    <div id="map" class="map"></div>
    <script>
    var openSpaceOl3 = new OpenSpaceOl3('14CA20BA51F7536BE0530B6CA40A4C2A', document.URL, OpenSpaceOl3.ALL_LAYERS);
	<!-- api key = 14CA20BA51F7536BE0530B6CA40A4C2A -->
    var map = new ol.Map({
        layers: [openSpaceOl3.getLayer()],
        logo: false,
        target: 'map',
        controls: ol.control.defaults({
            attributionOptions: ({
                collapsible: false
            })
        }).extend([
        new OpenSpaceOl3.OpenSpaceLogoControl({ className: 'openspaceol3-openspace-logo' })
        ]),
        view: new ol.View({
            projection: openSpaceOl3.getProjection(),
            center: [400000, 350000], // OS coords
            resolutions: openSpaceOl3.getResolutions(),
            resolution: openSpaceOl3.getMaxResolution() 
        })
    });
	
</script>
    
    
    
    <!--
    <script type="text/javascript">
      var map = new ol.Map({
        target: 'map',
        layers: [
          new ol.layer.Tile({
            source: new ol.source.MapQuest({layer: 'sat'})
          })
        ],
        view: new ol.View({
          center: ol.proj.transform([37.41, 8.82], 'EPSG:4326', 'EPSG:3857'),
          zoom: 4
        })
      });
    </script>
    -->
    
    
    
  </body>
</html>