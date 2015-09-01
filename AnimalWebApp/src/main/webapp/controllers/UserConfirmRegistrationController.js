//created by 41X
var animalAppControllers = angular.module('UserConfirmRegistrationController', []);

animalApp.controller('UserConfirmRegistrationController', ['$scope', '$location', 'userAccount',
                                                           function($scope, $location, userAccount) {
	
	if($location.search().username && $location.search().code){
		
		var userLogin = $location.search().username;
		console.log(userLogin);
		var code = $location.search().code;
		console.log(code);
		
		userAccount.confirmRegistration(userLogin,code);
	}
	
		
        
}]);