//created by 41X
var animalAppControllers = angular.module('UserProfileController', []);

animalApp.controller('UserProfileController', function($scope, userData, userAccount, hashPassword, localStorageService) {
		
		
	$scope.IsHidden = true;
	$scope.showPopup = function () {$scope.IsHidden =  false;}    
	$scope.closePopup = function () {$scope.IsHidden =  true;}
	
	$scope.userInfo = null;
	$scope.fields = null;
	
	if(!localStorageService.get("userId")){
		userAccount.refreshSession();  		
	} else {
		
		var id = localStorageService.get("userId");
		
		userData.getUser(id).success(function(data){				//webapi/users/user/{id}
			$scope.userInfo=data;
			$scope.fields = $scope.userInfo;
		}) 	
		
		userData.getUserAnimals(id).success(function(data){			//webapi/users/user/{id}/animals
			$scope.userAnimalInfo=data;
		});
		
	}
		
	
	
	
    $scope.submitUpdatedForm=function(){    	
    	
    	if($scope.passwordNew){    		
    		$scope.fields.password=hashPassword($scope.passwordNew);     		
    	}     	
    	
		userData.updateUser($scope.fields, $scope.fields.id); 
		
		$scope.IsHidden =  true;
	
	};	    
	
});