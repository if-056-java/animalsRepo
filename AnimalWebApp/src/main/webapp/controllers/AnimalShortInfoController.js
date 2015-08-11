angular.module('AnimalShortInfoController', ['AnimalShortInfoService'])
    .controller('AnimalShortInfoController', ['$scope', 'AnimalShortInfoService',  '$routeParams', '$window',
        function($scope, AnimalShortInfoService, $routeParams, $window) {
        
            var animalId = $routeParams.animalId;

            this.getAnimal = function(animalId) {
                AnimalShortInfoService.getAnimal(animalId)
                    .then(function(data) {
                        $scope.animal = data;
                    },
                    function(data) {
                        console.log('Animal retrieval failed.')
                    });
            };

            this.getAnimal(animalId);
    }]);