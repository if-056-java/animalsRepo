angular.module('AnimalsDetailedAdminController', ['AnimalsListAdminService'])
    .controller('AnimalsDetailedAdminController', ['$scope', 'AnimalsListAdminService', '$routeParams', '$window',
        function($scope, AnimalsListAdminService, $routeParams, $window) {
        
            var animalId = $routeParams.animalId;

            this.getAnimal = function(animalId) {
                AnimalsListAdminService.getAnimal(animalId)
                    .then(function(data) {
                        $scope.animal = data;
                    },
                    function(data) {
                        console.log('Animal retrieval failed.')
                    });
            };

            this.getAnimal(animalId);

            $scope.deleteAnimal = function() {
                AnimalsListAdminService.deleteAnimal($scope.animal.id)
                    .then(function(data) {
                        $window.location.href = "#/ua/user/home/animals";
                    },
                    function(data) {
                        $window.alert("Animal delete failed.");
                    });
            }
    }]);