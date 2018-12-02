
/* Simple example of OS Map + Gazetteer and Postcode lookup, all coordinates in EPSG:27700 */

/* to restrict the set of layers avalable use e.g. ["50KR", "50K", "SVR", "SV"] as the final constructor parameter */
/* See the OpenSpaceOl3 source code for a list of available layers */

var openSpaceOl3 = new OpenSpaceOl3('EB8FE9471221364EE0430B6CA40A6BDC', document.URL, OpenSpaceOl3.ALL_LAYERS);

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

function displayQuota(data) {
    if (data.length > 0) {
        $("#tilesAvailable").html(data[0]);
        $("#tilesUsed").html(data[1] + " at " + new Date().toLocaleTimeString() );

    }
}

// dont fetch quota too often, wait 2 seconds after pan/zoom ends
var quotaTimer = null;
map.on('moveend', function (evt) {

    if (quotaTimer)
        clearTimeout(quotaTimer);
    quotaTimer = setTimeout(function () { openSpaceOl3.asyncGetTileQuota(displayQuota); }, 2000);

});

openSpaceOl3.asyncGetTileQuota(displayQuota); // initial quota

$("#search-form").submit(function (e) {

    var search = $("#search-query").val();
    $('#result-select').find('option').remove(); // clear combo

    if (/\d/.test(search)) {
        //search string contained a digit, do a postcode search
        openSpaceOl3.asyncGeorefPostcode(search, function (data) {
            if (data.length == 2) {
                $("#result-select").append("<option value='select'>Select a result</option>");
                $("#result-select").append("<option value='" + data[0] + " " + data[1] + "'>" + search + "</option>");
            }
            else {
                $("#result-select").append("<option value='select'>Not found</option>");
            }
        });
    }
    else {
        openSpaceOl3.asyncGazetteerQuery(search, function (data) {
            if (data.length > 0) {
                $("#result-select").append("<option value='select'>Select a result</option>");
                $.each(data, function (i, item) {
                    $("#result-select").append("<option value='" + item.loc[0] + " " + item.loc[1] + "'>" + item.desc + "</option>");
                });
            }
            else {
                $("#result-select").append("<option value='select'>Not found</option>");
            }
        });
    }

    return false; // don't submit 

});

// pan to selected result
$('#result-select').change(function () {
    var strCoords = $(this).find(':selected').val().split(' ');
    if (strCoords.length == 2) {
        map.getView().setCenter([parseInt(strCoords[0],10), parseInt(strCoords[1],10)]);
    }
});

