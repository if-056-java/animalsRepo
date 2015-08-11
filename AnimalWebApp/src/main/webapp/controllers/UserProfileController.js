//created by 41X
var animalAppControllers = angular.module('UserProfileController', []);

animalApp.controller('UserProfileController', function($scope, userData, hashPassword, $rootScope) {
		
	console.log("before" + $rootScope.id);
	
	$scope.IsHidden = true;
	$scope.showPopup = function () {$scope.IsHidden =  false;}    
	$scope.closePopup = function () {$scope.IsHidden =  true;}
	
	$scope.userInfo = null;
	$scope.fields = null;
		
	var id = $rootScope.id;
	
	userData.getUser(id).success(function(data){				//webapi/users/user/{id}
		$scope.userInfo=data;		
	}) 	
	
	userData.getUserAnimals(id).success(function(data){			//webapi/users/user/{id}/animals
		$scope.userAnimalInfo=data;
	});
	    
	$scope.fields=$scope.userInfo;
	
    $scope.submitUpdateForm=function(){    	
    	
    	$scope.fields.password=hashPassword($scope.fields.passwordNew);
    	
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