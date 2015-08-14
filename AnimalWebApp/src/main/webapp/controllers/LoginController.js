//created by 41X
var animalAppControllers = angular.module('LoginController', []);

animalApp.controller('LoginController', function($scope, userAccount) {
	
	$scope.login=function(){
		
		var id = $scope.login.sessionId;
		var userName = $scope.login.userName;
		
		userAccount.login(userName, id);
		
	}	
	
});