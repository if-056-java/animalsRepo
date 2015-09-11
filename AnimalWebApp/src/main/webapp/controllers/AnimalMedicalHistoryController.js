angular.module('AnimalMedicalHistoryController', ['AnimalsDoctorModule', 'AnimalsDoctorValues', 'AnimalMedicalHistoryValues'])
    .controller('AnimalMedicalHistoryController', ['$scope', '$routeParams', '$window', 'AnimalsDoctorService', 'AnimalsDoctorValues', 'AnimalMedicalHistoryValues', '$filter',
        function($scope, $routeParams, $window, AnimalsDoctorService, AnimalsDoctorValues, AnimalMedicalHistoryValues, $filter) {

            AnimalsDoctorService.rolesAllowed('лікар');

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 3;

            $scope.currentLanguage = $window.localStorage.getItem('NG_TRANSLATE_LANG_KEY');

            var animalId = $routeParams.animalId;       //animal id
            $scope.animal = AnimalsDoctorValues.animal;  //animal
            $scope.animalImage = undefined;
            $scope.filter = AnimalMedicalHistoryValues.filter;            //filter
            $scope.totalItems = AnimalMedicalHistoryValues.totalItems;    //table rows count
            $scope.items = AnimalMedicalHistoryValues.items;

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            AnimalsDoctorService.getAnimal(animalId)
                .finally(function() {
                    $scope.animalImage = "resources/img/no_img.png";
                    if (AnimalsDoctorValues.animal.image != undefined) {
                        if (AnimalsDoctorValues.animal.image.length > 0) {
                            $scope.animalImage = AnimalsDoctorValues.animal.image;
                        }
                    }

                    $scope.contentLoading--;
                });

            /**
             * @return count of rows for pagination.
             */
            AnimalsDoctorService.getMedicalHistoryPagesCount(animalId)
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return list of medical history items.
             */
            AnimalsDoctorService.getMedicalHistoryItems(animalId)
                .then(function (response) {
                    if ($scope.items.values.length == 0) {
                        $scope.error = $filter('translate')("ERROR_NO_RECORDS");
                    }
                })
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return next page.
             */
            $scope.pageChanged = function() {
                AnimalsDoctorService.getMedicalHistoryItems(animalId);
            };

            /**
             * @return list of medical history items with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                AnimalsDoctorService.getMedicalHistoryItems(animalId);
            };

            $scope.delete = function(itemId) {
                AnimalsDoctorService.getMedicalHistoryItemDelete(itemId)
                    .then(function() {}
                    , function() {
                        $window.alert("Не вдалося видалити елемент.");
                    });
            }
        }]);
