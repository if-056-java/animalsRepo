angular.module('AnimalsListAdminController', ['ui.bootstrap', 'AnimalsListAdminService'])
    .controller('AnimalsListAdminController', ['$scope', 'AnimalsListAdminService', '$window', function($scope, AnimalsListAdminService, $window) {
        $scope.filter = {}
        $scope.filter.page = 1;
        $scope.filter.limit = '10';

        $scope.animals = [];

        var ACL = this;

        this.getPagesCount = function(filter) {
            AnimalsListAdminService.getPagesCount(filter)
                .then(function(data) {
                    $scope.totalItems = data.rowsCount;
                },
                function(data) {
                    console.log('Pages count retrieval failed.')
                });
        };

        this.getPagesCount($scope.filter);

        this.getAnimals = function(filter) {
            AnimalsListAdminService.getAnimals(filter)
                .then(function(data) {
                    $scope.animals = data;
                },
                function(data) {
                    console.log('Animals retrieval failed.')
                });
        };

        this.getAnimals($scope.filter);

        $scope.pageChanged = function() {
            ACL.getAnimals($scope.filter);
        };

        $scope.countChanged = function(count) {
            $scope.filter.limit = count;
            ACL.getAnimals($scope.filter);
        };
    }])
    .controller('AdminAnimalsFilterController', ['$scope', 'AnimalsListAdminService', function($scope, AnimalsListAdminService) {
        this.getAnimalTypes = function() {
            AnimalsListAdminService.getAnimalTypes()
                .then(function(data) {
                    $scope.animalTypes = data;
                },
                function(data) {
                    console.log('Animal retrieval failed.')
                });
        }

        this.getAnimalTypes();


        $scope.getAnimalBreeds = function() {
            AnimalsListAdminService.getAnimalBreeds($scope.filter.animal.type.id)
                .then(function(data) {
                    $scope.animalBreeds = data;
                },
                function(data) {
                    console.log('Animal breeds retrieval failed.')
                });
        }

        $scope.reset = function() {
            $scope.filter.animal.type = undefined;
            $scope.filter.animal.breed = undefined;
            $scope.filter.animal.sex = undefined;
            $scope.filter.animal.size = undefined;
        }

        $scope.doFilter = function() {
            AnimalsListAdminService.getPagesCount($scope.filter)
                .then(function(data) {
                    $scope.$parent.totalItems = data.rowsCount;
                },
                function(data) {
                    console.log('Pages count retrieval failed.')
                });

            AnimalsListAdminService.getAnimals($scope.filter)
                .then(function(data) {
                    $scope.$parent.animals = data;
                },
                function(data) {
                    console.log('Animals retrieval failed.')
                });
        };
    }]);