angular.module('AnimalMedicalHistoryEditorController', ['DPController', 'AnimalsDoctorModule', 'AnimalsDoctorValues', 'AnimalMedicalHistoryValues'])
    .controller('AnimalMedicalHistoryEditorController', ['$scope', '$routeParams', '$window', '$filter', 'AnimalsDoctorService', 'AnimalsDoctorValues', 'AnimalMedicalHistoryValues',
        function($scope, $routeParams, $window, $filter, AnimalsDoctorService, AnimalsDoctorValues, AnimalMedicalHistoryValues) {
            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 2;

            var animalId = $routeParams.animalId;       //animal id
            $scope.animal = AnimalsDoctorValues.animal;  //animal
            $scope.animalImage = undefined;
            $scope.itemTypes = AnimalMedicalHistoryValues.itemTypes;
            $scope.item = {};
            $scope.item.animalId = animalId;

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            AnimalsDoctorService.getAnimal(animalId)
                .finally(function() {
                    $scope.animalImage = "resources/img/noimg.png";
                    if (AnimalsDoctorValues.animal.image != undefined) {
                        if (AnimalsDoctorValues.animal.image.length > 0) {
                            $scope.animalImage = AnimalsDoctorValues.animal.image;
                        }
                    }

                    $scope.contentLoading--;
                });

            AnimalsDoctorService.getAnimalMedicalHistoryTypes()
                .finally(function() {
                    $scope.contentLoading--;
                });

            $scope.save = function() {
                $scope.item.date = $filter('date')($scope.item.date, 'yyyy-MM-dd');

                AnimalsDoctorService.updateAnimalMedicalHistoryItem($scope.item)
                    .then(function(){
                        $window.location.href = "#/ua/user/doctor/animals/medical_history/" + animalId;
                    }
                    , function() {
                        $window.alert("Не вдалося добавити запис.");
                    });
            }
        }]);
