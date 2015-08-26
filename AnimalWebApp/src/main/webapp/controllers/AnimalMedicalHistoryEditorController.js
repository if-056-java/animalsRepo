angular.module('AnimalMedicalHistoryEditorController', ['DPController', 'DoctorAnimalsModule', 'DoctorAnimalsValues', 'AnimalMedicalHistoryValues'])
    .controller('AnimalMedicalHistoryEditorController', ['$scope', '$routeParams', '$window', '$filter', 'DoctorAnimalsService', 'DoctorAnimalsValues', 'AnimalMedicalHistoryValues',
        function($scope, $routeParams, $window, $filter, DoctorAnimalsService, DoctorAnimalsValues, AnimalMedicalHistoryValues) {
            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 2;

            var animalId = $routeParams.animalId;       //animal id
            $scope.animal = DoctorAnimalsValues.animal;  //animal
            $scope.animalImage = undefined;
            $scope.itemTypes = AnimalMedicalHistoryValues.itemTypes;
            $scope.item = {};
            $scope.item.animalId = animalId;

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

            DoctorAnimalsService.getAnimalMedicalHistoryTypes()
                .finally(function() {
                    $scope.contentLoading--;
                });

            $scope.save = function() {
                $scope.item.date = $filter('date')($scope.item.date, 'yyyy-MM-dd');

                DoctorAnimalsService.setAnimalMedicalHistoryItem($scope.item)
                    .then(function(){
                        $window.location.href = "#/ua/user/doctor/animals/medical_history/" + animalId;
                    }
                    , function() {
                        $window.alert("Не вдалося добавити запис.");
                    });
            }
        }]);
