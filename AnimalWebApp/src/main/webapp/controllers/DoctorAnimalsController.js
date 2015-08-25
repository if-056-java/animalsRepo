angular.module('DoctorAnimals', ['DoctorAnimalsModule', 'nya.bootstrap.select', 'DPController', 'DoctorAnimalsValues'])
    .controller('DoctorAnimalsController', ['$scope', 'DoctorAnimalsService', 'DoctorAnimalsValues',
        function($scope, DoctorAnimalsService, DoctorAnimalsValues) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 4;

            $scope.filter = DoctorAnimalsValues.filter;            //filter
            $scope.totalItems = DoctorAnimalsValues.totalItems;    //table rows count
            $scope.animals = DoctorAnimalsValues.animals;          //animal instance

            /**
             * @return count of rows for pagination.
             */
            DoctorAnimalsService.getPagesCount()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return list of animals.
             */
            DoctorAnimalsService.getAnimals()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return next page.
             */
            $scope.pageChanged = function() {
                DoctorAnimalsService.getAnimals();
            };

            /**
             * @return list of animals with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                DoctorAnimalsService.getAnimals();
            };
    }])
    .controller('DoctorAnimalsFilterController', ['$scope', '$filter', 'DoctorAnimalsService', 'DoctorAnimalsValues',
        function($scope, $filter, DoctorAnimalsService, DoctorAnimalsValues) {

            $scope.filter = DoctorAnimalsValues.filter;                  //filter
            $scope.animalTypes = DoctorAnimalsValues.animalTypes;        //list of animal types
            $scope.animalServices = DoctorAnimalsValues.animalServices;  //list of animal services

            /**
             * @return list of animal types.
             */
            DoctorAnimalsService.getAnimalTypes()
                .finally(function() {
                    $scope.$parent.contentLoading--;
                });

            /**
             * @return list of animal types.
             */
            DoctorAnimalsService.getAnimalServices()
                .finally(function() {
                    $scope.$parent.contentLoading--;
                });

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.getAnimalBreeds = function() {
                $scope.filterAnimalBreedFlag = true;
                DoctorAnimalsService.getAnimalBreeds($scope.filter.animal.type.id)
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

                DoctorAnimalsService.getPagesCount();
                DoctorAnimalsService.getAnimals();
            };
    }]);