angular.module('AnimalsDoctorController', ['nya.bootstrap.select', 'DPController', 'AnimalsDoctorValues', 'AnimalsDoctorModule'])
    .controller('AnimalsDoctorController', ['$scope', 'AnimalsDoctorService', 'AnimalsDoctorValues', '$filter', '$window',
        function($scope, AnimalsDoctorService, AnimalsDoctorValues, $filter, $window) {

            AnimalsDoctorService.rolesAllowed('лікар');

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);

            $scope.filter = AnimalsDoctorValues.filter;            //filter
            $scope.totalItems = AnimalsDoctorValues.totalItems;    //table rows count
            $scope.animals = AnimalsDoctorValues.animals;          //animal instance

            $scope.currentLanguage = $window.localStorage.getItem('NG_TRANSLATE_LANG_KEY');

            /**
             * @return count of rows for pagination.
             */
            var getPagesCount = function() {
                AnimalsDoctorService.getPagesCount()
                    .finally(function () {
                        $scope.contentLoading--;
                    });
            }

            /**
             * @return list of animals.
             */
            var getAnimals = function() {
                $scope.error = undefined;

                AnimalsDoctorService.getAnimals()
                    .then(function (response) {
                        if ($scope.animals.values.length == 0) {
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
                $scope.contentLoading = 1;
                getAnimals();
            };

            /**
             * @return list of animals with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                $scope.contentLoading = 1;
                getAnimals();
            };
    }])
    .controller('AnimalsFilterDoctorController', ['$scope', '$filter', 'AnimalsDoctorService', 'AnimalsDoctorValues', '$window',
        function($scope, $filter, AnimalsDoctorService, AnimalsDoctorValues, $window) {

            $scope.filter = AnimalsDoctorValues.filter;                  //filter
            $scope.animalTypes = AnimalsDoctorValues.animalTypes;        //list of animal types
            $scope.animalServices = AnimalsDoctorValues.animalServices;  //list of animal services
            $scope.currentLanguage = $window.localStorage.getItem('NG_TRANSLATE_LANG_KEY');

            /**
             * @return list of animal types.
             */
            AnimalsDoctorService.getAnimalTypes();

            /**
             * @return list of animal types.
             */
            AnimalsDoctorService.getAnimalServices();

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.getAnimalBreeds = function() {
                $scope.filterAnimalBreedFlag = true;
                AnimalsDoctorService.getAnimalBreeds($scope.filter.animal.type.id)
                    .then(function(response) {
                        $scope.animalBreeds = response.data;
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