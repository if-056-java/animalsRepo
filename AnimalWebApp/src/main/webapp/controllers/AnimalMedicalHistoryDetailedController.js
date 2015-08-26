angular.module('AnimalMedicalHistoryDetailedController', ['DoctorAnimalsModule', 'DoctorAnimalsValues', 'AnimalMedicalHistoryValues'])
    .controller('AnimalMedicalHistoryDetailedController', ['$scope', '$routeParams', 'DoctorAnimalsService', 'DoctorAnimalsValues', 'AnimalMedicalHistoryValues',
        function($scope, $routeParams, DoctorAnimalsService, DoctorAnimalsValues, AnimalMedicalHistoryValues) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 2;

            var animalId = $routeParams.animalId;        //animal id
            var itemId = $routeParams.itemId;            //item id
            $scope.animal = DoctorAnimalsValues.animal;  //animal
            $scope.animalImage = undefined;
            $scope.item = AnimalMedicalHistoryValues.item;

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
             * @param itemId id of medical history item used for lookup.
             * @return medical history item instance.
             */
            DoctorAnimalsService.getMedicalHistoryItem(itemId)
                .finally(function() {
                    $scope.contentLoading--;
                });

        }]);