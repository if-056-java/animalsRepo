var animalAppControllers = angular.module('ContactsController', ['vcRecaptcha']);

animalApp.controller('ContactsController', function ($scope, vcRecaptchaService) {

    var myLatlng = new google.maps.LatLng(49.863400, 24.044500);
    var mapOptions = {
            zoom: 16,
            center: myLatlng
        };
    var map = new google.maps.Map(document.getElementById('map'), mapOptions);
    var marker = new google.maps.Marker({
            position: myLatlng,
            draggable: true,
            map: map,
            animation: google.maps.Animation.DROP,
            title: 'Наше розташування!'
        });
    google.maps.event.addListener(marker, 'click', toggleBounce);

    function toggleBounce() {

        if (marker.getAnimation() !== null) {
            marker.setAnimation(null);
        } else {
            marker.setAnimation(google.maps.Animation.BOUNCE);
        }
    }

    $scope.response = null;
    $scope.widgetId = null;
    $scope.model = {
        key: '6LdMAgMTAAAAAGYY5PEQeW7b3L3tqACmUcU6alQf'
    };
    $scope.setResponse = function (response) {
        $scope.response = response;
    };
    $scope.setWidgetId = function (widgetId) {
        $scope.widgetId = widgetId;
    };
    $scope.cbExpiration = function () {
        $scope.response = null;
    };
    $scope.submit = function () {
        var valid = 1;
                /**
                 * SERVER SIDE VALIDATION
                 *
                 * You need to implement your server side validation here.
                 * Send the reCaptcha response to the server and use some of the server side APIs to validate it
                 * See https://developers.google.com/recaptcha/docs/verify
                 */
        consolelog('sending the captcha response to the server', $scope.response);
        if (valid) {
            console.log('Success');
        } else {
            console.log('Failed validation');
                    // In case of a failed validation you need to reload the captcha
                    // because each response can be checked just once
            vcRecaptchaService.reload($scope.widgetId);
        }
    };
}
                    );