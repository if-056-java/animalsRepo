angular.module('AnimalsDetailed', ['AnimalsListService'])
    .controller('AnimalsDetailedController', ['$scope', 'AnimalsListService', '$routeParams', function($scope, AnimalsListService, $routeParams) {
        
        var animalId = $routeParams.animalId;

        $scope.animal = AnimalsListService.getAnimal(animalId);

    }]);