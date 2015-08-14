var animalAppControllers = angular.module('ContactsController', []);

animalApp.controller('ContactsController', function ($scope) {

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
        var valid = 1;

        if (vcRecaptchaService.getResponse() === "") { //if answer from Google is empty
            alert("Пройдіть перевірку на захист від спаму - клацніть на картинці - 'Я не робот'");
        } else {
            var feedback = { //prepare JSON for sending e-mail
                'mail': {
                    'signup': $scope.feedback.signature,
                    'email': $scope.feedback.email,
                    'text': $scope.feedback.text
                },
                'gRecaptchaResponse': $scope.response                 //g-captcah-reponse for server
            };
            console.log(feedback);

            $http.post('//localhost:8080/webapi/contacts/mail', feedback).success(function (response) {
                console.log(response);
                if (response.success === true) {
                    alert("Лист відправлено! Дякуємо за Ваш відгук!");
                    vcRecaptchaService.reload($scope.widgetId);
                } else {
                    alert("Лист не відправлено - помилка в  ");
                    vcRecaptchaService.reload($scope.widgetId);
                }
            });
        }
    }
});