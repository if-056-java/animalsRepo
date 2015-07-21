/**
 * Created by root on 08.07.2015.
 */
angular.module('app', ['ngRoute', 'AnimalsList', 'AnimalsDetailed', 'AnimalsDetailedEditor'])
    .config(function($routeProvider) {

        $routeProvider
            .when('/', {
                controller: 'AnimalsListController',
                templateUrl: 'app/views/animals_list.html'
            })
            .when('/animals_detailed/:animalId', {
                controller: 'AnimalsDetailedController',
                templateUrl: 'app/views/animals_detailed.html'
            })
            .when('/animals_detailed_editor/:animalId', {
                controller: 'AnimalsDetailedEditorController',
                templateUrl: 'app/views/animals_detailed_editor.html'
            })
            .otherwise({
                redirectTo: '/'
            });
    });