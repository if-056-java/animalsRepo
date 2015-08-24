var animalApp = angular.module('animalApp', [
    'ngRoute',
    'ngResource',
    'AdoptionModule',
    'AnimalRegistrationModule',
    'FindController',
    'LostController',
    'LoginController',
    'HomelessController',
    'AnimalsDetailController',
    'ContactsController',
    'StarterPageController',
    'AdminAnimals',
    'AdminAnimalsDetailed',
    'RegistrationController',
    'UserProfileController',
    'AnimalShortInfoController',
    'LocalStorageModule'
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
        templateUrl: 'views/admin_animals_detailed.html',
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
          templateUrl: 'views/admin_animals.html',
          controller: 'AdminAnimalsController'
        }).
        when('/ua/user/home/animals/:animalId', {
          templateUrl: 'views/admin_animals_detailed.html',
          controller: 'AdminAnimalsDetailedController'
        }).
        when('/ua/user/home/animals/editor/:animalId', {
            templateUrl: 'views/admin_animals_editor.html',
            controller: 'AdminAnimalsEditorController'
        }).
      when('/ua/user/registration', {
        templateUrl: 'views/registration.html',
        controller: 'RegistrationController'
      }).
      when('/ua/user/profile', {
        templateUrl: 'views/user_profile.html',
        controller: 'UserProfileController'
      }).
	  when('/ua/animal/:service/:animalId', {
        templateUrl: 'views/animal_short_info.html',
        controller: 'AnimalShortInfoController'
      }).
      when('/ua/animal/registration_homeless', {
        templateUrl: 'views/reg_homeless.html',
        controller: 'AnimalRegistrationController'
      }).
      otherwise({
        redirectTo: '/ua'
      });
  }]);

//Constants
animalApp.constant('RESOURCES', {
    RESOURCE: 'http://127.0.0.1:8080/',
        ANIMALS_FOR_ADOPTING_PAGINATOR: 'webapi/animals/adoption/pagenator',
        ANIMALS_FOR_ADOPTING: 'webapi/animals/adoption',
        ANIMAL_TYPES: 'webapi/animals/animal_types',
        ANIMAL_BREEDS: 'webapi/animals/animal_breeds/',
        ANIMAL_REGISTRATION: 'webapi/animals/animal',
        ANIMAL_REGISTRATION_IMAGE: 'webapi/animals/animal/image'
});

animalApp.config(function(localStorageServiceProvider){
	  localStorageServiceProvider
	  .setPrefix('AnimalWebApp')
	  //.setStorageCookie(45, '/')
	  // localStorageServiceProvider.setStorageCookieDomain('example.com');
	  // localStorageServiceProvider.setStorageType('sessionStorage');
	});


animalApp.controller('MainController', function($scope, $rootScope, localStorageService, userAccount) {
	
	if (!localStorageService.cookie.get("accessToken")) {
		localStorageService.clearAll();
	}
	
	
	$scope.logout = function() {       
        userAccount.logout();
    };
    
    $scope.session = function(value) {
        
        if (!localStorageService.get("userName")){
        	return false;
        } else {
        	$rootScope.userName=localStorageService.get("userName");
        	return true;
        } 
        
    };
      
});

