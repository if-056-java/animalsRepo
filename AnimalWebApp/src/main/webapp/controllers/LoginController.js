//created by 41X
var animalAppControllers = angular.module('LoginController', []);

animalApp.controller('LoginController', function($scope, $rootScope, $location) {
	
	$scope.showPopup = function () {
		$rootScope.Hide = false; 
		$rootScope.Show = false; 
		console.log($rootScope.Hide);
	} 
	
	
	$scope.setId=function(){
		
		var id = $scope.set.id;
		$rootScope.id = id;	
		$rootScope.socialLogin = "root";
		console.log("inside"+id); 
		$location.path("/ua/user/profile");			
	        
	};               
        
});