//created by 41X
var animalAppControllers = angular.module('MainController', []);

animalApp.controller('MainController', ['$scope', '$rootScope', 'localStorageService', 'userAccount',
                                        function($scope, $rootScope, localStorageService, userAccount) {
		
	if (!localStorageService.cookie.get("accessToken")) {
		localStorageService.clearAll();
	} else {
		if (localStorageService.get("memoryMe")=="ON")
				localStorageService.cookie.set("accessToken", localStorageService.get("accessToken"), 30);
		if (localStorageService.get("memoryMe")=="OFF")
	    		localStorageService.cookie.set("accessToken", localStorageService.get("accessToken"), 0.065);		
	}
	
	
	$scope.logout = function() {       
        userAccount.logout();
    };
    
    $scope.session = function(value) {
        
        if (!localStorageService.get("userName")){
        	return false;
        } else {
			$scope.userRole = localStorageService.get("userRole");
        	$rootScope.userName=localStorageService.get("userName");
        	return true;
        } 
        
    };
      
}]);