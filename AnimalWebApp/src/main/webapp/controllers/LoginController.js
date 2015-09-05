//created by 41X
var animalAppControllers = angular.module('LoginController', []);

animalApp.controller('LoginController', ['$scope', 'userAccount', 'hashPassword', 'localStorageService',
                                         function($scope, userAccount, hashPassword, localStorageService) {
	
	$scope.login=function(){			
		
		var userLogin = $scope.login.userLogin;
		var password = hashPassword($scope.login.password);
		
		var memoryMe ="OFF"
		if($scope.login.memoryMe) memoryMe ="ON";
		
		localStorageService.set("memoryMe", memoryMe);
		
		console.log($scope.login.password);
		console.log(password);
		
		userAccount.login(userLogin, password);
		
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
	
	
	
}]);