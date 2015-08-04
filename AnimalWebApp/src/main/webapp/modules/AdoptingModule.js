var adoptingModule = angular.module('AdoptionModule', ['ngResource']);

/*
adoptingModule.factory('AdoptionFactory', function ($resource) {
    return $resource('/webapi/animals/animal', {}, {
        create: {
            method: 'POST'
        }
    })
});

adoptingModule.factory('AdoptionFactory', function ($resource) {
    return $resource('/webapi/animals/:id', {}, {
        get: {
            method: 'GET',
            params: {id: '@id'},
            isArray:false
        },
        update: {
            method: 'PUT',
            params: {id: '@id'}
        },
        delete: {
            method: 'DELETE',
            params: {id: '@id'}
        }
    })
});
*/

adoptingModule.factory('AdoptionFactory', function ($resource) {
    return $resource('/webapi/animals/', {}, {
        query: {
            method: 'GET',
            params: {},
            isArray: true
        }
    })
});

/*
adoptingModule
    .factory('AdoptionFactory', function($resource) {
        var Test = $resource('/webapi/animals/:id');

        Test.get({id: 33}).$promise.then(function(tests){
            $scope.tests = tests;
        },function(err){
            //fail
        });

        Test.query().$promise.then(function(tests){
            $scope.tests = tests;
        }, function(err){

        });

        return Test;
    });
*/

