//created by 41X
var animalAppControllers = angular.module('UserProfileController', []);

animalApp.controller('UserProfileController', function($scope, userData, hashPassword, $rootScope) {
		
	console.log("before" + $rootScope.userId);
	
	$scope.IsHidden = true;
	$scope.showPopup = function () {$scope.IsHidden =  false;}    
	$scope.closePopup = function () {$scope.IsHidden =  true;}
	
	$scope.userInfo = null;
	$scope.fields = null;
		
	var id = $rootScope.userId;
	
	userData.getUser(id).success(function(data){				//webapi/users/user/{id}
		$scope.userInfo=data;
		$scope.fields = $scope.userInfo;
	}) 	
	
	userData.getUserAnimals(id).success(function(data){			//webapi/users/user/{id}/animals
		$scope.userAnimalInfo=data;
	});
	
	
    $scope.submitUpdateForm=function(){    	
    	
    	$scope.fields.password=hashPassword($scope.fields.passwordNew);
    	console.log($scope.fields.password);
    	
    	console.log($scope.fields);
    	
		userData.updateUser($scope.fields);       
	
	};
	
	$scope.setId=function(){
		
		var id = $scope.set.id;
		console.log("inside"+id);
		
		userData.getUser(id).success(function(data){				//webapi/users/user/{id}/
			$scope.userInfo=data;		
		})
		
		userData.getUserAnimals(id).success(function(data){			//webapi/users/user/{id}/animals
			$scope.userAnimalInfo=data;
		});	    
	    
	};         
	
});