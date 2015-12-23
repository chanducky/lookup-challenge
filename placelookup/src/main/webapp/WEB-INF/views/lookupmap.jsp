<!DOCTYPE html>
<html>
<head>

<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script type="text/javascript" src="http://maps.google.com/maps/api/js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript">
	var latitude = '${lat}';
	var longitude = '${lng}';

	// google map service start here
	var geocoder = new google.maps.Geocoder();

	function geocodePosition(pos) {
		geocoder
				.geocode(
						{
							latLng : pos
						},
						function(responses) {
							if (responses && responses.length > 0) {
								updateMarkerAddress(responses[0].formatted_address);
							} else {
								updateMarkerAddress('Cannot determine address at this location.');
							}
						});
	}

	function updateMarkerStatus(str) {
		document.getElementById('markerStatus').innerHTML = str;
	}

	function updateMarkerAddress(str) {
		document.getElementById('address').innerHTML = str;
	}

	function initialize() {
		var latLng = new google.maps.LatLng(latitude, longitude);
		var map = new google.maps.Map(document.getElementById('mapCanvas'), {
			zoom : 17,
			center : latLng,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		});
		var marker = new google.maps.Marker({
			position : latLng,
			title : 'Place Lookup',
			map : map,
			draggable : true
		});

		// Update current position info.
		geocodePosition(latLng);

		// Add dragging event listeners.
		google.maps.event.addListener(marker, 'dragstart', function() {
			updateMarkerAddress('Dragging...');
		});

		google.maps.event.addListener(marker, 'drag', function() {
			updateMarkerStatus('Dragging...');
		});

		google.maps.event.addListener(marker, 'dragend', function() {
			updateMarkerStatus('Drag ended');
			geocodePosition(marker.getPosition());
		});

	}

	function doOnLoad() {
		initialize();
	}
</script>

<style>
#mapCanvas {
	width: 100%;
	height: 400px;
	float: left;
	height: 400px;
}

#infoPanel {
	float: left;
	margin-left: 10px;
}

#infoPanel div {
	margin-bottom: 5px;
	margin-top: 10px;
}

#coorAdr {
	float: left;
	margin-left: 10px;
}

#coorAdr div {
	margin-bottom: 5px;
}
</style>

</head>
<body onload="doOnLoad();">
	<div id="contentwrapper">
		<div id="rh-col1">
			<div class="height10"></div>
			<div class="height10"></div>
			<div id="mapCanvas"></div>
			<div class="height10"></div>
			<div id="infoPanel">
				<b>Marker status:</b>
				<div id="markerStatus">
					<i>Click and drag the marker.</i>
				</div>
				<div class="height10"></div>
				<b>Location Details :</b>
				<div id="address"></div>
				<div class="height10"></div>
			</div>
		</div>
	</div>
</body>

</html>