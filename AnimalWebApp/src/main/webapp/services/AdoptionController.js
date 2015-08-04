var animalAppControllers = angular.module('AdoptionController', ['AdoptionModule']);

animalApp.controller('AdoptionController', ['$scope', 'AdoptionFactory', function($scope, AdoptionFactory) {

/*
    var getOne = AdoptionFactory.show({}, function(adoptionFactory){
        console.log('here get one: ' + getOne);
    });

    var getAll = AdoptionFactory.query(function(){
        console.log('here get all: ' + getAll);
        $scope.firstname = getAll;
    });
*/


    var query1 = AdoptionFactory.query(function(){
    var query = query1[1];
        $scope.id = query.id;
        $scope.type = query.type;
        $scope.kind = query.kind;
        $scope.dateOfBirth = query.dateOfBirth;
    });

}]);