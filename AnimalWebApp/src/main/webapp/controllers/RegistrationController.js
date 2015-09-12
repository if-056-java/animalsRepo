//created by 41X
var animalAppControllers = angular.module('RegistrationController', []);

animalApp.controller('RegistrationController', ['$scope', '$location', '$route', 'currentDate', 'AuthenticationService',
                                                'hashPassword', 'OauthAuthenticationService',
                                               function($scope, $location, $route, currentDate, AuthenticationService,
                                            		   hashPassword, OauthAuthenticationService) {
	
		$scope.submitRegForm=function(){
			
			$scope.errorConfirmMessage=false;        		
			$scope.fields.active = false; 
			$scope.fields.userRole = [{"id":3,"role":"гість"}]; //userRole=гість
			$scope.fields.userType = {"id":1,"type":"власник"};	//userType=власник		
			
			$scope.fields.registrationDate = currentDate;
			
			if ($scope.fields.password!=$scope.fields.password_confirm){
				$scope.errorConfirmMessage=true;				
			} else {				
				$scope.fields.password=hashPassword($scope.fields.password); 
				AuthenticationService.registerUser($scope.fields).then(
					function(result){					
						if(result.userId==0){
				        	$scope.errorRegistrationMessage1="Помилка реєстрації. Даний логін вже використовується!";
				        	console.log("Registration error. SocialLogin is already exist");
				        		
				        } else if (result.userId==1){	
				        	$location.path("/ua/user/confirmRegistration");	
						    $route.reload();						        
				        } else {
				        	console.log("error");
				        	$scope.errorRegistrationMessage3="Помилка реєстрації";
				       	}			
					},
					function(error){
						console.log(error);					
						$scope.errorRegistrationMessage2="Помилка реєстрації! Спробуйте ще раз";
					}
				);
			}     
	        
		}; 
		
		$scope.RegisterGoogle=function(){		
						
			OauthAuthenticationService.loginGoogle();		
			
		}
		
		$scope.RegisterFacebook=function(){		
			
			OauthAuthenticationService.loginFacebook();		
			
		}
		
		$scope.RegisterTwitter=function(){		
			
			OauthAuthenticationService.loginTwitter();		
			
		}
        
}]);

