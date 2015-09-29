var animalAppControllers = angular.module('StarterPageController', []);

animalApp.controller('StarterPageController', function($scope, $window) {
    $scope.currentLanguage = $window.localStorage.getItem('NG_TRANSLATE_LANG_KEY');

    //carousel
    $scope.slides = [
        {
            image: 'resources/img/carousel/car-1.jpg'
        },
        {
            image: 'resources/img/carousel/car-2.jpg'
        },
        {
            image: 'resources/img/carousel/car-3.jpg'
        }
    ];

});
