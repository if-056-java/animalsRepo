function initialize() {
// Введення широти та довготи
  var myLatlng = new google.maps.LatLng(49.863400,24.044500);
  var mapOptions = {
    zoom: 17,
    center: myLatlng
  }
  var map = new google.maps.Map(document.getElementById('map'), mapOptions);

  var marker = new google.maps.Marker({
      position: myLatlng,
      draggable:true,
      map: map,
      animation: google.maps.Animation.DROP,
// Підпис маркера
      title: 'Наше розташування!'
  });
      google.maps.event.addListener(marker, 'click', toggleBounce);
}

function toggleBounce() {

  if (marker.getAnimation() != null) {
    marker.setAnimation(null);
  } else {
    marker.setAnimation(google.maps.Animation.BOUNCE);
  }
}

google.maps.event.addDomListener(window, 'load', initialize);