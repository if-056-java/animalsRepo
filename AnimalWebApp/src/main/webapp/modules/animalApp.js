var animalApp = angular.module('animalApp', [
'ngRoute',
'ngResource',
'AdoptionModule',
'FindController',
'AdoptionController',
'LoginController',
'HomelessController',
'AnimalsDetailController',
'ContactsController',
'StarterPageController',
'RegistrationController',
'UserProfileController'
]);

animalApp .config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/ua', {
        templateUrl: 'views/main_view.html',
        controller: 'StarterPageController'
      }).
	  when('/ua/animal/adoption', {
        templateUrl: 'views/adoption.html',
        controller: 'AdoptionController'
      }).
	  when('/ua/animal/find', {
        templateUrl: 'views/find.html',
        controller: 'FindController'
      }).
	  when('/ua/animal/homeless', {
        templateUrl: 'views/reg_homeless.html',
        controller: 'HomelessController'
      }).
	  when('/ua/animal/detail', {
        templateUrl: 'views/animals_detailed.html',
        controller: 'AnimalsDetailController'
      }).
	  when('/ua/contacts', {
        templateUrl: 'views/contacts.html',
        controller: 'ContactsController'
      }).
      when('/ua/user/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginController'
      }).
      when('/ua/user/registration', {
        templateUrl: 'views/registration.html',
        controller: 'RegistrationController'
      }).
      when('/ua/user/profile', {
        templateUrl: 'views/user_profile.html',
        controller: 'UserProfileController'
      }).
      otherwise({
        redirectTo: '/ua'
      });
  }]);