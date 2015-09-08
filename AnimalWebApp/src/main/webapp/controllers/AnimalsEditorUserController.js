//created by 41X
var animalAppControllers = angular.module('AnimalsEditorUserController', ['AnimalsAdminModule', 'AnimalsAdminValues']);

animalApp.controller('AnimalsEditorUserController', ['$scope', 'userData', '$routeParams', '$window', '$filter', 'AnimalsAdminValues', 'AnimalsAdminService',
                                               function($scope, userData, $routeParams, $window, $filter, AnimalsAdminValues, AnimalsAdminService) {
		
	//initialize loading spinner
    var targetContent = document.getElementById('loading-block');    
    new Spinner(opts).spin(targetContent);   
    $scope.contentLoading = 0;
	
    var animalId = $routeParams.animalId;       //animal id   
    $scope.animalImage = undefined;

	$scope.contentLoading++;
	userData.getAnimal(animalId).then(
			function(result){
				$scope.animal=result;
				$scope.animalImage = "resources/img/noimg.png";				
                if (result.image != undefined) {
                    if (result.image.length > 0) {
                        $scope.animalImage = result.image;
                    }
                }
				$scope.contentLoading--;
			},
			function(error){				
				console.log(error)
				$scope.contentLoading--;
			}
		);
	
	/**
     * @return list of animal types.
     */
    AnimalsAdminService.getAnimalTypes()
        .finally(function() {
        	$scope.animalTypes = AnimalsAdminValues.animalTypes;
        });
    
    /**
     * @return list of animal breeds according to animal type.
     */
    $scope.getAnimalBreeds = function() {
        $scope.filterAnimalBreedFlag = true;
        AnimalsAdminService.getAnimalBreeds($scope.animal.type.id)
            .then(function(data) {
                $scope.animalBreeds = data;
            })
            .finally(function() {
                $scope.filterAnimalBreedFlag = false;
            });
    }
	
	
	$scope.updateAnimal = function(isValid) {
		if(!isValid){
            return;
        }

        $scope.animal.dateOfBirth = $filter('date')($scope.animal.dateOfBirth, 'yyyy-MM-dd');
        $scope.animal.dateOfSterilization = $filter('date')($scope.animal.dateOfSterilization, 'yyyy-MM-dd');
        $scope.animal.dateOfRegister = $filter('date')($scope.animal.dateOfRegister, 'yyyy-MM-dd');
        if (typeof $scope.animal.breed != "undefined") {
            if (typeof $scope.animal.breed.id == "undefined") {
                $scope.animal.breed = {breedUa: $scope.animal.breed};
            }
        }

        if ($scope.imageFile != undefined) {
            $scope.animal.image = $scope.imageFile['filename'] + '\n' + $scope.imageFile['base64'];
        }
        
        userData.updateAnimal($scope.animal)
            .then(function(result) {
                $window.location.href = "#/ua/user/profile/animals/" + animalId;
            },
            function(data) {
                $window.alert("Animal update failed.");
            });
    }
	
}]);