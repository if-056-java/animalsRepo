angular.module('AnimalsDoctorController', ['nya.bootstrap.select', 'DPController', 'AnimalsDoctorValues', 'AnimalsDoctorModule'])
    .controller('AnimalsDoctorController', ['$scope', 'AnimalsDoctorService', 'AnimalsDoctorValues',
        function($scope, AnimalsDoctorService, AnimalsDoctorValues) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 4;

            $scope.filter = AnimalsDoctorValues.filter;            //filter
            $scope.totalItems = AnimalsDoctorValues.totalItems;    //table rows count
            $scope.animals = AnimalsDoctorValues.animals;          //animal instance

            /**
             * @return count of rows for pagination.
             */
            AnimalsDoctorService.getPagesCount()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return list of animals.
             */
            AnimalsDoctorService.getAnimals()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return next page.
             */
            $scope.pageChanged = function() {
                AnimalsDoctorService.getAnimals();
            };

            /**
             * @return list of animals with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                AnimalsDoctorService.getAnimals();
            };
    }])
    .controller('AnimalsFilterDoctorController', ['$scope', '$filter', 'AnimalsDoctorService', 'AnimalsDoctorValues',
        function($scope, $filter, AnimalsDoctorService, AnimalsDoctorValues) {

            $scope.filter = AnimalsDoctorValues.filter;                  //filter
            $scope.animalTypes = AnimalsDoctorValues.animalTypes;        //list of animal types
            $scope.animalServices = AnimalsDoctorValues.animalServices;  //list of animal services

            /**
             * @return list of animal types.
             */
            AnimalsDoctorService.getAnimalTypes()
                .finally(function() {
                    $scope.$parent.contentLoading--;
                });

            /**
             * @return list of animal types.
             */
            AnimalsDoctorService.getAnimalServices()
                .finally(function() {
                    $scope.$parent.contentLoading--;
                });

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.getAnimalBreeds = function() {
                $scope.filterAnimalBreedFlag = true;
                AnimalsDoctorService.getAnimalBreeds($scope.filter.animal.type.id)
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
            }

            /**
             * @return list of animals according to filter values.
             */
            $scope.doFilter = function() {
                $scope.filter.animal.dateOfRegister = $filter('date')($scope.filter.animal.dateOfRegister, 'yyyy-MM-dd');

                AnimalsDoctorService.getPagesCount();
                AnimalsDoctorService.getAnimals();
            };
    }]);