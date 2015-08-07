var animalAppControllers = angular.module('UserProfileController', []);

animalApp.controller('UserProfileController', function($scope, $http) {
	
	//$scope.userInfo = 
	$http.get("/webapi/users/25")
    .success(function(data) {
    	$scope.userInfo = data;
    	console.log(data);
    })
	
	$scope.IsHidden = true;
    $scope.showPopup = function () {
        //show div.
        $scope.IsHidden =  false;
    }    
    $scope.closePopup = function () {
    	 //hide div.
        $scope.IsHidden =  true;
    }
	
});