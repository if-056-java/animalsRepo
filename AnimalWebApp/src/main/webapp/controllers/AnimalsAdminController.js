angular.module('AnimalsAdminController', ['AnimalsAdminModule', 'nya.bootstrap.select', 'DPController', 'AnimalsAdminValues'])
    .controller('AnimalsAdminController', ['$scope', 'AnimalsAdminService', 'AnimalsAdminValues', '$filter',
        function($scope, AnimalsAdminService, AnimalsAdminValues, $filter) {

            AnimalsAdminService.rolesAllowed("модератор");

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);

            $scope.filter = AnimalsAdminValues.filter;            //filter
            $scope.totalItems = AnimalsAdminValues.totalItems;    //table rows count
            $scope.animals = AnimalsAdminValues.animals;          //animal instance


            /**
             * @return count of rows for pagination.
             */
            var getPagesCount = function() {
                AnimalsAdminService.getPagesCount()
                    .finally(function () {
                        $scope.contentLoading--;
                    });
            }

            /**
             * @return list of animals.
             */
            var getAnimals = function() {
                $scope.error = undefined;

                AnimalsAdminService.getAnimals()
                    .catch(function (response) {
                        if (response.status == 404) {
                            $scope.error = $filter('translate')("ERROR_NO_ANIMALS");
                        }
                    })
                    .finally(function () {
                        $scope.contentLoading--;
                    });
            }

            $scope.getData = function() {
                $scope.contentLoading = 2;
                getPagesCount();
                getAnimals();
            }

            $scope.getData();

            /**
             * @return next page.
             */
            $scope.pageChanged = function() {
                getAnimals();
            };

            /**
             * @return list of animals with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                getAnimals();
            };

            /**
             * @param id
             * sending message to Twitter.
             */
            $scope.sendTwitter = function (id) {
                AnimalsAdminService.sendTwitter(id);
            };

            /**
             * @param id
             * sending message to Facebook.
             */
            $scope.sendFacebook = function (id) {
                AnimalsAdminService.sendFacebook(id);
            };
    }])
    .controller('AnimalsFilterAdminController', ['$scope', '$filter', 'AnimalsAdminService', 'AnimalsAdminValues', '$window',
        function($scope, $filter, AnimalsAdminService, AnimalsAdminValues, $window) {

            $scope.filter = AnimalsAdminValues.filter;                  //filter
            $scope.animalTypes = AnimalsAdminValues.animalTypes;        //list of animal types
            $scope.animalServices = AnimalsAdminValues.animalServices;  //list of animal services
            $scope.currentLanguage = $window.localStorage.getItem('NG_TRANSLATE_LANG_KEY');

            /**
             * @return list of animal types.
             */
            AnimalsAdminService.getAnimalTypes();

            /**
             * @return list of animal types.
             */
            AnimalsAdminService.getAnimalServices();

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.getAnimalBreeds = function() {
                $scope.filterAnimalBreedFlag = true;
                AnimalsAdminService.getAnimalBreeds($scope.filter.animal.type.id)
                    .then(function(data) {
                        $scope.animalBreeds = data;
                    })
                    .finally(function() {
                        $scope.filterAnimalBreedFlag = false;
                    });
            }

            /**
             * reset filter values.
             */
            $scope.reset = function() {
                $scope.filter.animal.transpNumber = undefined;
                $scope.filter.animal.service = undefined;
                $scope.filter.animal.type = undefined;
                $scope.filter.animal.breed = undefined;
                $scope.filter.animal.sex = undefined;
                $scope.filter.animal.dateOfRegister = undefined;

                $scope.submit(true);
            }

            /**
             * @return list of animals according to filter values.
             */
            $scope.submit = function(isValid) {
                if(!isValid){
                    return;
                }

                $scope.filter.animal.dateOfRegister = $filter('date')($scope.filter.animal.dateOfRegister, 'yyyy-MM-dd');

                $scope.$parent.getData();
            };
    }]);