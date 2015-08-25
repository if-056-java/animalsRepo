//created by 41X
var animalAppControllers = angular.module('RegistrationController', []);

animalApp.controller('RegistrationController', function($scope, currentDate, userData, userAccount, hashPassword) {
	
		$scope.submitRegForm=function(){
			
			        		
			$scope.fields.active = true; 
			$scope.fields.userRole = {"id": 3}; //userRole=гість
			$scope.fields.userType = {"id": 1};	//userType=власник		
			
			$scope.fields.registrationDate = currentDate;
			
			$scope.fields.password=hashPassword($scope.fields.password); 				        
	       
	        userAccount.registerUser($scope.fields);
	        
		};               
        
});