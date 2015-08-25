//created by 41X
var animalAppControllers = angular.module('LoginController', []);

animalApp.controller('LoginController', function($scope, userAccount, hashPassword, localStorageService) {
	
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
	
});