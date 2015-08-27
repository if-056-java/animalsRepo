angular.module('AnimalsDetailedAdminController', ['AnimalsAdminModule', 'AnimalsAdminValues'])
    .controller('AnimalsDetailedAdminController', ['$scope', '$routeParams', '$window', 'AnimalsAdminService', 'AnimalsAdminValues',
        function($scope, $routeParams, $window, AnimalsAdminService, AnimalsAdminValues) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 1;

            var animalId = $routeParams.animalId;       //animal id
            $scope.animal = AnimalsAdminValues.animal;  //animal
            $scope.animalImage = undefined;

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            AnimalsAdminService.getAnimal(animalId)
                .finally(function() {
                    $scope.animalImage = "resources/img/noimg.png";
                    if (AnimalsAdminValues.animal.image != undefined) {
                        if (AnimalsAdminValues.animal.image.length > 0) {
                            $scope.animalImage = AnimalsAdminValues.animal.image;
                        }
                    }

                    $scope.contentLoading--;
                });

            /**
             * delete animal.
             */
            $scope.deleteAnimal = function() {
                AnimalsAdminService.deleteAnimal($scope.animal.id)
                    .then(function(data) {
                        $window.location.href = "#/ua/user/home/animals";
                    },
                    function(data) {
                        $window.alert("Animal delete failed.");
                    });
            }

        }]);