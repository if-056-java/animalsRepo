angular.module('AnimalMedicalHistoryController', ['DoctorAnimalsModule', 'DoctorAnimalsValues'])
    .controller('AnimalMedicalHistoryController', ['$scope', '$routeParams', 'DoctorAnimalsService', 'DoctorAnimalsValues',
        function($scope, $routeParams, DoctorAnimalsService, DoctorAnimalsValues) {
            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 1;

            var animalId = $routeParams.animalId;       //animal id
            $scope.animal = DoctorAnimalsValues.animal;  //animal
            $scope.animalImage = undefined;
            $scope.filter = DoctorAnimalsValues.medicalHistory.filter;            //filter
            $scope.totalItems = DoctorAnimalsValues.medicalHistory.totalItems;    //table rows count

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
             * @return count of rows for pagination.
             */
            DoctorAnimalsService.getMedicalHistoryPagesCount(animalId)
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return list of medical history items.
             */
            DoctorAnimalsService.getMedicalHistoryItems(animalId)
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return next page.
             */
            $scope.pageChanged = function() {
                DoctorAnimalsService.getMedicalHistoryItems(animalId);
            };

            /**
             * @return list of medical history items with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                DoctorAnimalsService.getMedicalHistoryItems(animalId);
            };
        }]);
