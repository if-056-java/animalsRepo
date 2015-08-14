angular.module('AdminAnimals', ['ui.bootstrap', 'AdminAnimalsModule'])
    .controller('AdminAnimalsController', ['$scope', 'AdminAnimalsService', '$window', function($scope, AdminAnimalsService, $window) {

        $scope.filter = {}          //filter
        $scope.filter.page = 1;     //page number
        $scope.filter.limit = '10'; //row count per page

        $scope.animals = [];        //animal instance

        var ACL = this;             //for colling class methods from scope methods

        /**
         * @param filter instance used for lookup.
         * @return count of rows for pagination.
         */
        this.getPagesCount = function(filter) {
            AdminAnimalsService.getPagesCount(filter)
                .then(function(data) {
                    $scope.totalItems = data.rowsCount;
                },
                function(data) {
                    console.log('Pages count retrieval failed.')
                });
        };

        this.getPagesCount($scope.filter);

        /**
         * @param filter instance used for lookup.
         * @return list of animals.
         */
        this.getAnimals = function(filter) {
            AdminAnimalsService.getAnimals(filter)
                .then(function(data) {
                    $scope.animals = data;
                },
                function(data) {
                    console.log('Animals retrieval failed.')
                });
        };

        this.getAnimals($scope.filter);

        /**
         * @return next page.
         */
        $scope.pageChanged = function() {
            ACL.getAnimals($scope.filter);
        };

        /**
         * @return list of animals with given count of rows.
         */
        $scope.countChanged = function(count) {
            $scope.filter.limit = count;
            ACL.getAnimals($scope.filter);
        };
    }])
    .controller('AdminAnimalsFilterController', ['$scope', 'AdminAnimalsService', function($scope, AdminAnimalsService) {
        /**
         * @return list of animal types.
         */
        this.getAnimalTypes = function() {
            AdminAnimalsService.getAnimalTypes()
                .then(function(data) {
                    $scope.animalTypes = data;
                },
                function(data) {
                    console.log('Animal retrieval failed.')
                });
        }

        this.getAnimalTypes();

        /**
         * @return list of animal breeds according to animal type.
         */
        $scope.getAnimalBreeds = function() {
            $scope.filterAnimalBreedFlag = true;
            AdminAnimalsService.getAnimalBreeds($scope.filter.animal.type.id)
                .then(function(data) {
                    $scope.animalBreeds = data;
                },
                function(data) {
                    console.log('Animal breeds retrieval failed.')
                })
                .finally(function() {
                    $scope.filterAnimalBreedFlag = false;
                });

        }

        /**
         * reset filter values.
         */
        $scope.reset = function() {
            $scope.filter.animal.type = undefined;
            $scope.filter.animal.breed = undefined;
            $scope.filter.animal.sex = undefined;
            $scope.filter.animal.size = undefined;
        }

        /**
         * @return list of animals according to filter values.
         */
        $scope.doFilter = function() {
            AdminAnimalsService.getPagesCount($scope.filter)
                .then(function(data) {
                    $scope.$parent.totalItems = data.rowsCount;
                },
                function(data) {
                    console.log('Pages count retrieval failed.')
                });

            AdminAnimalsService.getAnimals($scope.filter)
                .then(function(data) {
                    $scope.$parent.animals = data;
                },
                function(data) {
                    console.log('Animals retrieval failed.')
                });
        };
    }]);