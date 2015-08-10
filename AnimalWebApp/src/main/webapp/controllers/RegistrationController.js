//created by 41X
var animalAppControllers = angular.module('RegistrationController', []);

animalApp.controller('RegistrationController', function($scope, currentDate, userData, hashPassword) {
	
		$scope.submitRegForm=function(){
			
			        		
			$scope.fields.active = true; 
			$scope.fields.userRole = {"id": 3};
			$scope.fields.userType = {"id": 1};
			
			
			$scope.fields.registrationDate = currentDate;
			
			$scope.fields.password=hashPassword($scope.fields.password); 
				        
	        userData.createUser($scope.fields); 
	        
		};               
        
});