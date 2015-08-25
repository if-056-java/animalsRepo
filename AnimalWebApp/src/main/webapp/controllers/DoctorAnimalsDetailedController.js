angular.module('DoctorAnimalsDetailed', ['DoctorAnimalsModule', 'DoctorAnimalsValues'])
    .controller('DoctorAnimalsDetailedController', ['$scope', '$routeParams', 'DoctorAnimalsService', 'DoctorAnimalsValues',
        function($scope, $routeParams, DoctorAnimalsService, DoctorAnimalsValues) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 1;

            var animalId = $routeParams.animalId;       //animal id
            $scope.animal = DoctorAnimalsValues.animal;  //animal
            $scope.animalImage = undefined;

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            DoctorAnimalsService.getAnimal(animalId)
                .finally(function() {
                    $scope.animalImage = "resources/img/noimg.png";
                    if (DoctorAnimalsValues.animal.image != undefined) {
                        if (DoctorAnimalsValues.animal.image.length > 0) {
                            $scope.animalImage = DoctorAnimalsValues.animal.image;
                        }
                    }

                    $scope.contentLoading--;
                });

            /**
             * delete animal.
             */
            $scope.deleteAnimal = function() {
                DoctorAnimalsService.deleteAnimal($scope.animal.id)
                    .then(function(data) {
                        $window.location.href = "#/ua/user/home/animals";
                    },
                    function(data) {
                        $window.alert("Animal delete failed.");
                    });
            }

        }]);