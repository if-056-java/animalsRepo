//created by 41X
var animalAppControllers = angular.module('LoginController', []);

animalApp.controller('LoginController', ['$scope', '$location', '$route', 'AuthenticationService', 'hashPassword', 'localStorageService', 'OauthAuthenticationService',
                                         function($scope, $location, $route, AuthenticationService, hashPassword, localStorageService, OauthAuthenticationService) {
	
	$scope.login=function(){			
		
		var userLogin = $scope.login.userLogin;
		var password = hashPassword($scope.login.password);
		
		var memoryMe ="OFF"
		if($scope.login.memoryMe) memoryMe ="ON";
		
		localStorageService.set("memoryMe", memoryMe);
		
		
		AuthenticationService.login(userLogin, password).then(
				function(result){					
					if(result.userId==1){
		        		$scope.errorConfirmMessage="Для входу потрібно підтвердити реєстрацію!";		        		  		
		        	} else {			        	
				        $location.path("/ua/user/profile");	
				        $route.reload();
		        	}					
				},
				function(error){
					$scope.errorMessage="Помилка входу. Перевірте свої дані!";
				}
		);
		
	}	
	
	$scope.loginGoogle=function(){		
		
		if (localStorageService.cookie.get("refreshGoogleToken")) {
			OauthAuthenticationService.loginDirectGoogle();			
		} else {
			OauthAuthenticationService.loginGoogle();
		}
		
	}
	
	$scope.loginFacebook=function(){		
		
		OauthAuthenticationService.loginFacebook();		
		
	}
	
	$scope.loginTwitter=function(){		
		
		if (localStorageService.cookie.get("twitterToken") && localStorageService.cookie.get("twitterSecret")) {
			OauthAuthenticationService.loginDirectTwitter();			
		} else {
			OauthAuthenticationService.loginTwitter();		
		}
	}
	
	
	
}]);