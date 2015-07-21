angular.module('AnimalsDetailedEditor', ['angular-bootstrap-select', 'angular-bootstrap-select.extra', 'AnimalsListService'])
    .controller('AnimalsDetailedEditorController', ['$scope', 'AnimalsListService', '$routeParams', '$window', function($scope, AnimalsListService, $routeParams, $window) {
        
        var animalId = $routeParams.animalId;
        //console.log(animalId);
        $scope.animal = AnimalsListService.getAnimal(animalId);

        $scope.setAnimal = function() {
            AnimalsListService.setAnimal($scope.animal);
            $window.location.href = '#/animals_detailed/' + animalId;
        }
    }]);