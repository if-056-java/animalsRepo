var animalAppControllers = angular.module('UserProfileController', []);

animalApp.controller('UserProfileController', function($scope) {
	
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