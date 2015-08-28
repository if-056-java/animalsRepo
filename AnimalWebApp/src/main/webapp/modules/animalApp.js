var animalApp = angular.module('animalApp', [
    'ngRoute',
    'ngResource',
    'AdoptionModule',
    'AnimalRegistrationModule',
    'FindController',
    'LostController',
    'LoginController',
    'HomelessController',
    'ContactsController',
    'StarterPageController',
    'AnimalsAdminController',
    'AnimalsDetailedAdminController',
    'AnimalsEditorAdminController',
    'AnimalsDoctorController',
    'AnimalsDetailedDoctorController',
    'AnimalMedicalHistoryController',
    'AnimalMedicalHistoryEditorController',
    'AnimalMedicalHistoryDetailedController',
    'RegistrationController',
    'UserProfileController',
    'AnimalShortInfoController',
    'MainController',
    'LocalStorageModule'
]);

animalApp .config(['$routeProvider',
    function($routeProvider) {
        $routeProvider
            .when('/ua', {
                templateUrl: 'views/main_view.html',
                controller: 'StarterPageController'
            })
            .when('/ua/animal/adoption', {
                templateUrl: 'views/adoption.html',
                controller: 'AdoptionController'
            })
            .when('/ua/animal/find', {
                templateUrl: 'views/find_lost.html',
                controller: 'FindController'
            })
            .when('/ua/animal/lost', {
                templateUrl: 'views/find_lost.html',
                controller: 'LostController'
            })
            .when('/ua/animal/homeless', {
                templateUrl: 'views/reg_homeless.html',
                controller: 'HomelessController'
            })
            .when('/ua/animal/detail', {
                templateUrl: 'views/admin_animals_detailed.html',
                controller: 'AnimalsDetailController'
            })
            .when('/ua/contacts', {
                templateUrl: 'views/contacts.html',
                controller: 'ContactsController'
            })
            .when('/ua/user/login', {
                templateUrl: 'views/login.html',
                controller: 'LoginController'
            })
            .when('/ua/user/home/animals', {
                templateUrl: 'views/admin_animals.html',
                controller: 'AnimalsAdminController'
            })
            .when('/ua/user/home/animals/:animalId', {
                templateUrl: 'views/admin_animals_detailed.html',
                controller: 'AnimalsDetailedAdminController'
            })
            .when('/ua/user/home/animals/editor/:animalId', {
                templateUrl: 'views/admin_animals_editor.html',
                controller: 'AnimalsEditorAdminController'
            })
            .when('/ua/user/doctor/animals', {
                templateUrl: 'views/doctor_animals.html',
                controller: 'AnimalsDoctorController'
            })
            .when('/ua/user/doctor/animals/:animalId', {
                templateUrl: 'views/doctor_animals_detailed.html',
                controller: 'AnimalsDetailedDoctorController'
            })
            .when('/ua/user/doctor/animals/medical_history/:animalId', {
                templateUrl: 'views/animal_medical_history.html',
                controller: 'AnimalMedicalHistoryController'
            })
            .when('/ua/user/doctor/animals/medical_history/:animalId/editor', {
                templateUrl: 'views/animal_medical_history_editor.html',
                controller: 'AnimalMedicalHistoryEditorController'
            })
            .when('/ua/user/doctor/animals/medical_history/:animalId/detailed/:itemId', {
                templateUrl: 'views/animal_medical_history_detailed.html',
                controller: 'AnimalMedicalHistoryDetailedController'
            })
            .when('/ua/user/registration', {
                templateUrl: 'views/registration.html',
                controller: 'RegistrationController'
            })
            .when('/ua/user/profile', {
                templateUrl: 'views/user_profile.html',
                controller: 'UserProfileController'
            })
            .when('/ua/animal/:service/:animalId', {
                templateUrl: 'views/animal_short_info.html',
                controller: 'AnimalShortInfoController'
            })
            .when('/ua/animal/registration_homeless', {
                templateUrl: 'views/reg_homeless.html',
                controller: 'AnimalHomelessRegController'
            })
	        .when('/ua/animal/registration_owned', {
        	    templateUrl: 'views/reg_owned.html',
        	    controller: 'AnimalOwnedRegController'
      	    })
            .otherwise({
                redirectTo: '/ua'
            });
    }]);

//Constants
animalApp.constant('RESOURCES', {
    RESOURCE: 'http://127.0.0.1:8080/',
    ANIMALS_FOR_ADOPTING_PAGINATOR: 'webapi/animals/adoption/pagenator',
    ANIMALS_FOR_ADOPTING: 'webapi/animals/adoption',
    ANIMAL_TYPES: '/webapi/animals/animal_types',
    ANIMAL_BREEDS: '/webapi/animals/animal_breeds/',
    ANIMAL_SERVICES: '/webapi/animals/animal_services',
    ANIMAL_REGISTRATION: 'webapi/animals/animal',
    ANIMAL_REGISTRATION_IMAGE: 'webapi/animals/animal/image',
    ANIMALS_FOR_ADMIN: '/webapi/admin/animals',
    ANIMALS_FOR_ADMIN_PAGINATOR: '/webapi/admin/animals/paginator',
    ANIMAL_FOR_ADMIN: '/webapi/admin/animals/',
    ANIMAL_FOR_ADMIN_DELETE: '/webapi/admin/animals/',
    ANIMAL_FOR_ADMIN_UPDATE: '/webapi/admin/animals/editor',
    MEDICAL_HISTORY_ITEMS_FOR_DOCTOR: '/webapi/doctor/medical_history/',
    MEDICAL_HISTORY_ITEMS_PAGINATOR_FOR_DOCTOR: '/webapi/doctor/medical_history/paginator/',
    MEDICAL_HISTORY_ITEM_FOR_DOCTOR_DELETE: '/webapi/doctor/medical_history/item/',
    MEDICAL_HISTORY_ITEM_FOR_DOCTOR_UPDATE: '/webapi/doctor/medical_history/item',
    MEDICAL_HISTORY_TYPES: '/webapi/animals/medical_history/types'
});

animalApp.config(function(localStorageServiceProvider){
	  localStorageServiceProvider.setPrefix('AnimalWebApp');	  
	});



