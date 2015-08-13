angular.module('AdminAnimalsDetailed', ['AdminAnimalsModule'])
    .controller('AdminAnimalsDetailedController', ['$scope', 'AdminAnimalsService', '$routeParams', '$window',
        function($scope, AdminAnimalsService, $routeParams, $window) {
        
            var animalId = $routeParams.animalId;   //route parameter - animal id

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            this.getAnimal = function(animalId) {
                AdminAnimalsService.getAnimal(animalId)
                    .then(function(data) {
                        $scope.animal = data;
                        if ($scope.animal.image != undefined) {
                            $scope.animal.image = $scope.animal.image + "?timestamp=" + new Date().getTime();
                        }
                    },
                    function(data) {
                        console.log('Animal retrieval failed.')
                    });
            };

            this.getAnimal(animalId);

            /**
             * delete animal.
             */
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