var animalAppControllers = angular.module('AdoptionController', ['AdoptionModule']);

animalApp.controller('AdoptionController', ['$scope', 'AdoptionFactory', function($scope, AdoptionFactory) {

    $scope.animalsForAdopting = AdoptionFactory.query();

}]);