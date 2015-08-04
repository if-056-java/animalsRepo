var animalAppControllers = angular.module('animalAppControllers', []);

animalApp.controller('ContactsController', function($scope) {

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
      title: 'Наше розташування!'
  });
      google.maps.event.addListener(marker, 'click', toggleBounce);

function toggleBounce() {

  if (marker.getAnimation() != null) {
    marker.setAnimation(null);
  } else {
    marker.setAnimation(google.maps.Animation.BOUNCE);
  }
}

google.maps.event.addDomListener(window, 'load', initialize);
});

animalApp.controller('FindController', function($scope) {
});  

animalApp.controller('AdoptionController', function($scope) {
});

animalApp.controller('LoginController', function($scope) {
});

animalApp.controller('StarterPageController', function($scope) {
});

animalApp.controller('HomelessController', function($scope) {
});

