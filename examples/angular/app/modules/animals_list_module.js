angular.module('AnimalsList', ['angular-bootstrap-select', 'angular-bootstrap-select.extra', 'AnimalsListService'])
    .controller('AnimalsListController', ['$scope', 'AnimalsListService', '$window', function($scope, AnimalsListService, $window) {
        
        $scope.animals = AnimalsListService.getAnimals();
        
        $scope.removeAnimal = function(animalId) {
            $scope.animals = AnimalsListService.removeAnimal(animalId);    
        }
    }]);