//created by 41X
var animalAppControllers = angular.module('LoginController', []);

animalApp.controller('LoginController', function($scope, userAccount) {
	
//	
//	
//	$scope.setId=function(){
//		
//		var id = $scope.set.id;
//		$rootScope.id = id;	
//		$rootScope.socialLogin = "root";
//		console.log("inside"+id); 
//		$location.path("/ua/user/profile");			
//	        
//	};  
	
	$scope.login=function(){
		
		var id = $scope.login.sessionId;
		var userName = $scope.login.userName;
		
		userAccount.login(userName, id);
		
	}	
	
});