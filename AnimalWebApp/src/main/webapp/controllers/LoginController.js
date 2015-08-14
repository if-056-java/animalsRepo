//created by 41X
var animalAppControllers = angular.module('LoginController', []);

animalApp.controller('LoginController', function($scope, userAccount, hashPassword) {
	
	$scope.login=function(){
		
		var userLogin = $scope.login.userLogin;
		var password = hashPassword($scope.login.password);
		
		console.log($scope.login.password);
		console.log(password);
		
		userAccount.login(userLogin, password);
		
	}	
	
});