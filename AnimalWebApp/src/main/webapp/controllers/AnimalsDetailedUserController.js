//created by 41X
var animalAppControllers = angular.module('AnimalsDetailedUserController', [ ]);

animalApp.controller('AnimalsDetailedUserController', ['$scope', 'UserDataService', '$routeParams', '$window', 'AnimalsAdminValues',
                                               function($scope, UserDataService, $routeParams, $window, AnimalsAdminValues) {
	
	 //initialize loading spinner
     var targetContent = document.getElementById('loading-block');
     new Spinner(opts).spin(targetContent);
     //This variable decides when spinner loading for contentis closed.
     $scope.contentLoading = 1;

     $scope.currentLanguage = $window.localStorage.getItem('NG_TRANSLATE_LANG_KEY');

     var animalId = $routeParams.animalId;       //animal id
     $scope.animal = AnimalsAdminValues.animal;  //animal
     $scope.animalImage = undefined;	
	
     UserDataService.getAnimal(animalId).then(
			function(result){				
				angular.copy(result, AnimalsAdminValues.animal);
				$scope.animalImage = "resources/img/noimg.png";
				if (AnimalsAdminValues.animal.image != undefined) {
                    if (AnimalsAdminValues.animal.image.length > 0) {
                        $scope.animalImage = AnimalsAdminValues.animal.image;
                    }
                }
				$scope.contentLoading--;
			},
			function(error){				
				console.log(error)
				$scope.contentLoading--;
			}
		);
	
     $scope.deleteAnimal = function() {
    	 UserDataService.deleteAnimal($scope.animal.id)
            .then(function(data) {
                $window.location.href = "#/ua/user/profile";
            },
            function(data) {
                $window.alert("Animal delete failed.");
            });
    }
	
}]);