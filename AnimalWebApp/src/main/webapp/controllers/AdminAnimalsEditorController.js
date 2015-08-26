angular.module('AdminAnimalsEditor', ['AdminAnimalsModule', 'AdminAnimalsValues', 'nya.bootstrap.select', 'DPController', 'naif.base64'])
    .controller('AdminAnimalsEditorController', ['$scope', '$window', '$filter', '$routeParams', 'AdminAnimalsService', 'AdminAnimalsValues',
        function($scope, $window, $filter, $routeParams, AdminAnimalsService, AdminAnimalsValues) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 4;

            var animalId = $routeParams.animalId;                       //animal id

            /**
             * @return list of animal types.
             */
            AdminAnimalsService.getAnimalTypes()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return list of animal services.
             */
            AdminAnimalsService.getAnimalServices()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            AdminAnimalsService.getAnimal(animalId)
                .finally(function() {
                    $scope.contentLoading--
                });

            $scope.$watch('contentLoading', function(newValue) {
                if (newValue != 1) {
                    return;
                }

                $scope.animalTypes = AdminAnimalsValues.animalTypes;        //list of animal types
                $scope.animalServices = AdminAnimalsValues.animalServices;  //list of animal services
                $scope.animal = AdminAnimalsValues.animal;                  //animal
                $scope.animalImage = "resources/img/noimg.png";
                if (AdminAnimalsValues.animal.image != undefined) {
                    if (AdminAnimalsValues.animal.image.length > 0) {
                        $scope.animalImage = AdminAnimalsValues.animal.image;
                    }
                }

                $scope.contentLoading--;
            });

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.getAnimalBreeds = function() {
                $scope.filterAnimalBreedFlag = true;
                AdminAnimalsService.getAnimalBreeds($scope.animal.type.id)
                    .then(function(data) {
                        $scope.animalBreeds = data;
                    })
                    .finally(function() {
                        $scope.filterAnimalBreedFlag = false;
                    });
            }

            /**
             * update animal.
             */
            $scope.setAnimal = function() {
                $scope.animal.dateOfBirth = $filter('date')($scope.animal.dateOfBirth, 'yyyy-MM-dd');
                $scope.animal.dateOfSterilization = $filter('date')($scope.animal.dateOfSterilization, 'yyyy-MM-dd');
                $scope.animal.dateOfRegister = $filter('date')($scope.animal.dateOfRegister, 'yyyy-MM-dd');
                if (typeof $scope.animal.breed != "undefined") {
                    if (typeof $scope.animal.breed.id == "undefined") {
                        $scope.animal.breed = {breedUa: $scope.animal.breed};
                    }
                }

                if ($scope.imageFile != undefined) {
                    $scope.animal.image = $scope.imageFile['filename'] + '\n' + $scope.imageFile['base64'];
                }
                
                AdminAnimalsService.updateAnimal($scope.animal)
                    .then(function(data) {
                        $window.location.href = "#/ua/user/home/animals/" + $scope.animal.id;
                    },
                    function(data) {
                        console.log('Animal update failed.')
                    });
            }
        }
    ]);
