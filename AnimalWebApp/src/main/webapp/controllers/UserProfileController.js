//created by 41X
var animalAppControllers = angular.module('UserProfileController', []);

animalApp.controller('UserProfileController', function($scope, userData, hashPassword, localStorageService) {
		
		
	$scope.IsHidden = true;
	$scope.showPopup = function () {$scope.IsHidden =  false;}    
	$scope.closePopup = function () {$scope.IsHidden =  true;}
	
	$scope.userInfo = null;
	$scope.fields = null;
		
	var id = localStorageService.get("userId");
	
	console.log("id from localstorage - "+id);
	
	userData.getUser(id).success(function(data){				//webapi/users/user/{id}
		$scope.userInfo=data;
		$scope.fields = $scope.userInfo;
	}) 	
	
	userData.getUserAnimals(id).success(function(data){			//webapi/users/user/{id}/animals
		$scope.userAnimalInfo=data;
	});
	
	
    $scope.submitUpdateForm=function(){    	
    	
    	if($scope.passwordNew){    		
    		$scope.fields.password=hashPassword($scope.passwordNew); 
    		console.log("changing password");
    	} else {
    		console.log("not changing password")
    	} 
    	
    	console.log($scope.fields);
    	
		userData.updateUser($scope.fields, $scope.fields.id);       
	
	};	    
	
});