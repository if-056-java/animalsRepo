var animalApp = angular.module('animalApp', [
'ngRoute',
'FindController',
'AdoptionController',
'LoginController',
'HomelessController',
'AnimalsDetailController',
'ContactsController',
'StarterPageController'
]);

animalApp .config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/ua', {
        templateUrl: 'view/main_view.html',
        controller: 'StarterPageController'
      }).
	  when('/ua/animal/adoption', {
        templateUrl: 'view/adoption.html',
        controller: 'AdoptionController'
      }).
	  when('/ua/animal/find', {
        templateUrl: 'view/find.html',
        controller: 'FindController'
      }).
	  when('/ua/animal/homeless', {
        templateUrl: 'view/reg_homeless.html',
        controller: 'HomelessController'
      }).
	  when('/ua/animal/detail', {
        templateUrl: 'view/animals_detailed.html',
        controller: 'AnimalsDetailController'
      }).
	  when('/ua/contacts', {
        templateUrl: 'view/contacts.html',
        controller: 'ContactsController'
      }).
      when('/ua/user/login', {
        templateUrl: 'view/login.html',
        controller: 'LoginController'
      }).
      otherwise({
        redirectTo: '/ua'
      });
  }]);