angular.module('AnimalMedicalHistoryController', ['DoctorAnimalsModule', 'DoctorAnimalsValues', 'AnimalMedicalHistoryValues'])
    .controller('AnimalMedicalHistoryController', ['$scope', '$routeParams', '$window', 'DoctorAnimalsService', 'DoctorAnimalsValues', 'AnimalMedicalHistoryValues',
        function($scope, $routeParams, $window, DoctorAnimalsService, DoctorAnimalsValues, AnimalMedicalHistoryValues) {
            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 3;

            var animalId = $routeParams.animalId;       //animal id
            $scope.animal = DoctorAnimalsValues.animal;  //animal
            $scope.animalImage = undefined;
            $scope.filter = AnimalMedicalHistoryValues.filter;            //filter
            $scope.totalItems = AnimalMedicalHistoryValues.totalItems;    //table rows count
            $scope.items = AnimalMedicalHistoryValues.items;

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

            $scope.delete = function(itemId) {
                DoctorAnimalsService.getMedicalHistoryItemDelete(itemId)
                    .then(function() {}
                    , function() {
                        $window.alert("Не вдалося видалити елемент.");
                    });
            }
        }]);
