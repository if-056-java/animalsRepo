var animalAppControllers = angular.module('AdoptionController', ['AdoptionModule']);

animalApp.controller('AdoptionController', ['$scope', 'AdoptionFactory', function($scope, AdoptionFactory) {
	
	$scope.header_a_f_l="Тварини на адопцію :"
	
    $scope.animalsForAdopting = AdoptionFactory.query();

}]);