var animalApp = angular.module('animalApp', [
'ngRoute',
'ngResource',
'FindController',
	'FindController',
'LostController',
'LoginController',
'HomelessController',
'AnimalsDetailController',
'ContactsController',
'StarterPageController',
  'AnimalsListAdminController',
  'AnimalsDetailedAdminController',
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
        templateUrl: 'views/find_lost.html',
        controller: 'FindController'
      }).
	  when('/ua/animal/lost', {
        templateUrl: 'views/find_lost.html',
        controller: 'LostController'
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
        when('/ua/user/home/animals', {
          templateUrl: 'views/animals_admin.html',
          controller: 'AnimalsListAdminController'
        }).
        when('/ua/user/home/animals/:animalId', {
          templateUrl: 'views/animals_detailed.html',
          controller: 'AnimalsDetailedAdminController'
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

//Constants
animalApp.constant('RESOURCES', {
    RESOURCE: 'http://127.0.0.1:8080/',
        ANIMALS_FOR_ADOPTING_PAGINATOR: 'webapi/animals/adoption/pagenator',
        ANIMALS_FOR_ADOPTING: 'webapi/animals/adoption/'
});
