var animalAppControllers = angular.module('ContactsController', ['vcRecaptcha']);

animalApp.controller('ContactsController', function ($http, $scope, vcRecaptchaService) {

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
        var opts = {
            lines: 10 // The number of lines to draw
            , length: 10 // The length of each line
            , width: 8 // The line thickness
            , radius: 18 // The radius of the inner circle
            , scale: 1 // Scales overall size of the spinner
            , corners: 1 // Corner roundness (0..1)
            , color: 'green' // #rgb or #rrggbb or array of colors
            , opacity: 0.35 // Opacity of the lines
            , rotate: 0 // The rotation offset
            , direction: 1 // 1: clockwise, -1: counterclockwise
            , speed: 2 // Rounds per second
            , trail: 53 // Afterglow percentage
            , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
            , zIndex: 2e9 // The z-index (defaults to 2000000000)
            , className: 'spinner' // The CSS class to assign to the spinner
            , top: '50%' // Top position relative to parent
            , left: '80%' // Left position relative to parent
            , shadow: false // Whether to render a shadow
            , hwaccel: false // Whether to use hardware acceleration
            , position: 'absolute' // Element positioning
        };

        //initialize loading spinner
        var target = document.getElementById('loading-block')
        new Spinner(opts).spin(target);
        
        $scope.spinnerloading = 0;
        $scope.startupmessage = 1;
        $scope.okmessage = 0;
        $scope.errormessage = 0;

    $scope.response = null;
    $scope.widgetId = null;
    $scope.model = {
        key: '6LdeAQsTAAAAAFYDoWkW8_zNKVu1tu6D-RlLfgnR'
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
       if (vcRecaptchaService.getResponse() === "") { //if answer from Google is empty
           $scope.spinnerloading = 0;
           $scope.startupmessage = 1;
           $scope.okmessage = 0;
           $scope.errormessage = 0;

        } else {
            $scope.spinnerloading = 1;
           $scope.startupmessage = 0;
           $scope.okmessage = 0;
           $scope.errormessage = 0;

           $scope.feedback.gRecaptchaResponse = $scope.response ;                //g-captcah-reponse for server
            console.log($scope.feedback);
            /* MAKE AJAX REQUEST to our server with g-captcha-string */

            $http.post('//localhost:8080/webapi/contacts/mail', $scope.feedback).success(function (response) {
				console.log(response);
                if (response.success === true) {
                    $scope.spinnerloading = 0;
                    $scope.startupmessage = 0;
                    $scope.okmessage = 1;
                    $scope.errormessage = 0;

                    $scope.feedback.signup='';
                    $scope.feedback.email='';
                    $scope.feedback.text='';
                    vcRecaptchaService.reload($scope.widgetId);

                } else {
                    $scope.spinnerloading = 0;
                    $scope.startupmessage = 0;
                    $scope.okmessage = 0;
                    $scope.errormessage = 1;

                    vcRecaptchaService.reload($scope.widgetId);
                }
            })
                        .error(function (error) {
                    $scope.spinnerloading = 0;
                    $scope.startupmessage = 0;
                    $scope.okmessage = 0;
                    $scope.errormessage = 1;

                        // In case of a failed validation you need to reload the captcha
                        // because each response can be checked just once
                    vcRecaptchaService.reload($scope.widgetId);

                }
                    );
        }
    }
}
)