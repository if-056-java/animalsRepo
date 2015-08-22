angular.module('AdminAnimals', ['AdminAnimalsModule', 'nya.bootstrap.select', 'DPController', 'AdminAnimalsValues'])
    .controller('AdminAnimalsController', ['$scope', 'AdminAnimalsService', 'AdminAnimalsValues',
        function($scope, AdminAnimalsService, AdminAnimalsValues) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 4;

            $scope.filter = AdminAnimalsValues.filter;            //filter
            $scope.totalItems = AdminAnimalsValues.totalItems;    //table rows count
            $scope.animals = AdminAnimalsValues.animals;          //animal instance

            /**
             * @return count of rows for pagination.
             */
            AdminAnimalsService.getPagesCount()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return list of animals.
             */
            AdminAnimalsService.getAnimals()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return next page.
             */
            $scope.pageChanged = function() {
                AdminAnimalsService.getAnimals();
            };

            /**
             * @return list of animals with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                AdminAnimalsService.getAnimals();
            };
    }])
    .controller('AdminAnimalsFilterController', ['$scope', '$filter', 'AdminAnimalsService', 'AdminAnimalsValues',
        function($scope, $filter, AdminAnimalsService, AdminAnimalsValues) {

            $scope.filter = AdminAnimalsValues.filter;                  //filter
            $scope.animalTypes = AdminAnimalsValues.animalTypes;        //list of animal types
            $scope.animalServices = AdminAnimalsValues.animalServices;  //list of animal services

            /**
             * @return list of animal types.
             */
            AdminAnimalsService.getAnimalTypes()
                .finally(function() {
                    $scope.$parent.contentLoading--;
                });

            /**
             * @return list of animal types.
             */
            AdminAnimalsService.getAnimalServices()
                .finally(function() {
                    $scope.$parent.contentLoading--;
                });

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.getAnimalBreeds = function() {
                $scope.filterAnimalBreedFlag = true;
                AdminAnimalsService.getAnimalBreeds($scope.filter.animal.type.id)
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

                AdminAnimalsService.getPagesCount();
                AdminAnimalsService.getAnimals();
            };
    }]);