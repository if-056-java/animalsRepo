var animalAppControllers = angular.module('RegistrationController', []);

animalApp.controller('RegistrationController', ['$scope', '$location', '$route', 'currentDate', 'AuthenticationService',
                                                'hashPassword', 'OauthAuthenticationService', '$filter',
                                               function($scope, $location, $route, currentDate, AuthenticationService,
                                            		   hashPassword, OauthAuthenticationService, $filter) {
		$scope.errors = [];
		
		var locale = localStorage.getItem("NG_TRANSLATE_LANG_KEY");
			
		$scope.submitRegForm=function(){
			
			$scope.errorConfirmMessage=false;        		
			$scope.fields.isActive = false; 
			$scope.fields.userRole = [{"id":3,"role":"guest"}]; //userRole=гість
			$scope.fields.userType = {"id":1,"type":"owner"};	//userType=власник		
			
			$scope.fields.registrationDate = currentDate;			
							
			$scope.fields.password=hashPassword($scope.password); 
			AuthenticationService.registerUser($scope.fields, locale).then(
				function(result){
					
					if(result.userId==0){
				        $scope.errorRegistrationMessage1=true;
				        console.log("Registration error. SocialLogin is already exist");	
					} else if(result.userId==-1){
						$scope.errorRegistrationMessage2=true;
				        console.log("Registration error. Email is already exist");						
					} else if (result.userId==1){	
				        $location.path("/ua/user/confirmRegistration");	
						$route.reload();			        
				    } else if (result.length>0){
				    	for (i = 0; i < result.length; i++) {				    		
						    $scope.errors.push({msg: $filter('translate')(result[i])});
						}
				    	 $scope.errorRegistrationMessage4=true;				    	 
				    } else {
				        console.log("error");
				        $scope.errorRegistrationMessage3=true;
				    }			
				}
			);    
	        
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

