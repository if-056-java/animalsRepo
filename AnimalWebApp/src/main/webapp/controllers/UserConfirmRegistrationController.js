//created by 41X
var animalAppControllers = angular.module('UserConfirmRegistrationController', []);

animalApp.controller('UserConfirmRegistrationController', ['$scope', '$location', '$route', 'userAccount', 
                                                           function($scope, $location, $route, userAccount) {
	
	if($location.search().username && $location.search().code){
		
		var userLogin = $location.search().username;		
		var code = $location.search().code;
		
		
		userAccount.confirmRegistration(userLogin,code).then(
				function(result){
					console.log(result.accessToken);		        	
			        $location.path("/ua/user/profile");	
			        $route.reload();					
				},
				function(error){
					console.log(error);					
					$scope.errorRegistrationConfirmMessage = error;					
				}
			);
	}
	
		
        
}]);