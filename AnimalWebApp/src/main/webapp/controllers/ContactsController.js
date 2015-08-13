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

        if(vcRecaptchaService.getResponse() === ""){ //if answer from Google is empty
            alert("Пройдіть перевірку на захист від спаму - клацніть на картинці - 'Я не робот'")
        }else {
            var feedback = { //prepare JSON for sending e-mail
				'mail' : {
                'signup':$scope.feedback.signature,
                'email':$scope.feedback.email,
                'text':$scope.feedback.text
				},
                'g-recaptcha-response': $scope.response  //g-captcah-reponse for server
            }

        }
        console.log('sending the captcha response to the server', feedback);
 /* MAKE AJAX REQUEST to our server with g-captcha-string */
        $http.post('//localhost:8080/webapi/contacts/mail',feedback).success(function(response){
            if(response.error === 0){
                alert("Лист відправлено! 				Дякуємо за Ваш відгук!");
            }else{
                alert("Лист не відправлено - помилка в  ");
            }
        })
            .error(function(error){

            })
            /**
                 * SERVER SIDE VALIDATION
                 *
                 * You need to implement your server side validation here.
                 * Send the reCaptcha response to the server and use some of the server side APIs to validate it
                 * See https://developers.google.com/recaptcha/docs/verify
                 */

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