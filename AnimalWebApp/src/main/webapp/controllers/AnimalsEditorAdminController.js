angular.module('AnimalsEditorAdminController', ['nya.bootstrap.select', 'DPController', 'naif.base64', 'AnimalsAdminModule', 'AnimalsAdminValues'])
    .controller('AnimalsEditorAdminController', ['$scope', '$window', '$filter', '$routeParams', 'AnimalsAdminService', 'AnimalsAdminValues',
        function($scope, $window, $filter, $routeParams, AnimalsAdminService, AnimalsAdminValues) {

            AnimalsAdminService.rolesAllowed("модератор");

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 1;

            $scope.currentLanguage = $window.localStorage.getItem('NG_TRANSLATE_LANG_KEY');

            var animalId = $routeParams.animalId;                       //animal id

            var initialize = function() {
                if (AnimalsAdminValues.animalTypes.values.length === 0  ||
                    AnimalsAdminValues.animalServices.values.length === 0 ||
                    AnimalsAdminValues.animal.id == undefined ||
                    $scope.contentLoading === 0) {
                    return;
                }

                $scope.animalTypes = AnimalsAdminValues.animalTypes;        //list of animal types
                $scope.animalServices = AnimalsAdminValues.animalServices;  //list of animal services
                $scope.animal = angular.copy(AnimalsAdminValues.animal);     //animal
                $scope.animalImage = "resources/img/no_img.png";

                if (AnimalsAdminValues.animal.image != undefined) {
                    if (AnimalsAdminValues.animal.image.length > 0) {
                        $scope.animalImage = AnimalsAdminValues.animal.image;
                    }
                }

                if ($scope.animal.type != undefined) {
                    $scope.getAnimalBreeds();
                }

                $scope.contentLoading--;
            }

            /**
             * @return list of animal types.
             */
            AnimalsAdminService.getAnimalTypes()
                .finally(function() {
                    initialize();
                });

            /**
             * @return list of animal services.
             */
            AnimalsAdminService.getAnimalServices()
                .finally(function() {
                    initialize();
                });

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            AnimalsAdminService.getAnimal(animalId)
                .finally(function() {
                    initialize();
                });

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.getAnimalBreeds = function() {
                $scope.filterAnimalBreedFlag = true;
                AnimalsAdminService.getAnimalBreeds($scope.animal.type.id)
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
            $scope.submit = function(isValid) {
                if(!isValid){
                    return;
                }

                $scope.animal.dateOfBirth = $filter('date')($scope.animal.dateOfBirth, 'yyyy-MM-dd');
                $scope.animal.dateOfSterilization = $filter('date')($scope.animal.dateOfSterilization, 'yyyy-MM-dd');
                $scope.animal.dateOfRegister = $filter('date')($scope.animal.dateOfRegister, 'yyyy-MM-dd');
                if (typeof $scope.animal.breed != "undefined") {
                    if (typeof $scope.animal.breed.id == "undefined") {
                        if ($scope.currentLanguage == "en") {
                            $scope.animal.breed = {breedEn: $scope.animal.breed};
                        } else {
                            $scope.animal.breed = {breedUa: $scope.animal.breed};
                        }

                    }
                }

                if ($scope.imageFile != undefined) {
                    $scope.animal.image = $scope.imageFile['filename'] + '\n' + $scope.imageFile['base64'];
                }

                AnimalsAdminService.updateAnimal($scope.animal)
                    .then(function(data) {
                        angular.copy($scope.animal, AnimalsAdminValues.animal);
                        AnimalsAdminValues.animal.image = data.filePath;
                        $window.location.href = "#/ua/user/home/animals/" + $scope.animal.id;
                    },
                    function(data) {
                        console.log('Animal update failed.')
                    });
            }

            /*
             * delete image
             */
            $scope.deleteImage = function() {
                if ($scope.animalImage === "resources/img/no_img.png") {
                    $window.alert($filter('translate')("DELETE_IMAGE_NO_IMAGE"));
                    return;
                }

                if (!confirm($filter('translate')("DELETE_IMAGE_CONFIRM"))) {
                    return;
                }

                AnimalsAdminService.deleteAnimalImage($scope.animal.id)
                    .then(function(data) {
                        $scope.animal.image = undefined;
                        $scope.animalImage = "resources/img/no_img.png";
                    }, function(data) {
                        $window.alert($filter('translate')("DELETE_IMAGE_FAILED"));
                    });
            }
        }
    ]);
