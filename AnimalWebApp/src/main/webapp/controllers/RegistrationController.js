//created by 41X
var animalAppControllers = angular.module('RegistrationController', []);

animalApp.controller('RegistrationController', ['$scope', 'currentDate', 'userData', 'userAccount', 'hashPassword',
                                               function($scope, currentDate, userData, userAccount, hashPassword) {
	
		$scope.submitRegForm=function(){
			
			$scope.errorConfirmMessage=false;        		
			$scope.fields.active = false; 
			$scope.fields.userRole = {"id": 3}; //userRole=гість
			$scope.fields.userType = {"id": 1};	//userType=власник		
			
			$scope.fields.registrationDate = currentDate;
			
			if ($scope.fields.password!=$scope.fields.password_confirm){
				$scope.errorConfirmMessage=true;				
			} else {				
				$scope.fields.password=hashPassword($scope.fields.password); 
				userAccount.registerUser($scope.fields);
			}     
	        
		}; 
		
		$scope.RegisterGoogle=function(){		
						
			userAccount.loginGoogle();		
			
		}
		
		$scope.RegisterFacebook=function(){		
			
			userAccount.loginFacebook();		
			
		}
		
		$scope.RegisterTwitter=function(){		
			
			userAccount.loginTwitter();		
			
		}
        
}]);

