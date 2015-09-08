//created by 41X
var animalAppControllers = angular.module('LoginController', []);

animalApp.controller('LoginController', ['$scope', '$location', '$route', 'userAccount', 'hashPassword', 'localStorageService',
                                         function($scope, $location, $route, userAccount, hashPassword, localStorageService) {
	
	$scope.login=function(){			
		
		var userLogin = $scope.login.userLogin;
		var password = hashPassword($scope.login.password);
		
		var memoryMe ="OFF"
		if($scope.login.memoryMe) memoryMe ="ON";
		
		localStorageService.set("memoryMe", memoryMe);
		
		console.log($scope.login.password);
		console.log(password);
		
		userAccount.login(userLogin, password).then(
				function(result){					
					if(result.userId==1){
		        		$scope.errorConfirmMessage="Для входу потрібно підтвердити реєстрацію!";
		        		console.log("Confirm registration with email");		        		
		        	} else {			        	
				        $location.path("/ua/user/profile");	
				        $route.reload();
		        	}					
				},
				function(error){
					console.log(error);					
					$scope.errorMessage="Помилка входу. Перевірте свої дані!";
				}
		);
		
	}	
	
	$scope.loginGoogle=function(){		
		
		if (localStorageService.cookie.get("refreshGoogleToken")) {
			userAccount.loginDirectGoogle();			
		} else {
			userAccount.loginGoogle();
		}
		
	}
	
	$scope.loginFacebook=function(){		
		
		userAccount.loginFacebook();		
		
	}
	
	$scope.loginTwitter=function(){		
		
		if (localStorageService.cookie.get("twitterToken") && localStorageService.cookie.get("twitterSecret")) {
			userAccount.loginDirectTwitter();			
		} else {
			userAccount.loginTwitter();		
		}
	}
	
	
	
}]);