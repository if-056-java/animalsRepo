angular.module('AdminAnimalsDetailed', ['AdminAnimalsModule'])
    .controller('AdminAnimalsDetailedController', ['$scope', 'AdminAnimalsService', '$routeParams', '$window',
        function($scope, AdminAnimalsService, $routeParams, $window) {
            $scope.goBack = function() {
                $window.history.back();
            }
        
            var animalId = $routeParams.animalId;

            this.getAnimal = function(animalId) {
                AdminAnimalsService.getAnimal(animalId)
                    .then(function(data) {
                        $scope.animal = data;
                    },
                    function(data) {
                        console.log('Animal retrieval failed.')
                    });
            };

            this.getAnimal(animalId);

            $scope.deleteAnimal = function() {
                AdminAnimalsService.deleteAnimal($scope.animal.id)
                    .then(function(data) {
                        $window.location.href = "#/ua/user/home/animals";
                    },
                    function(data) {
                        $window.alert("Animal delete failed.");
                    });
            }
    }]);