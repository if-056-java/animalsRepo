var animalAppControllers = angular.module('UserDetailedAdminController', []);

animalApp.controller('UserDetailedAdminController', ['$scope', 'UserModerationService', 'localStorageService', '$routeParams', '$window', '$filter',                                               
                                               function($scope, UserModerationService, localStorageService, $routeParams, $window, $filter) {
	
	//initialize loading spinner
    var targetContent = document.getElementById('loading-block');
    new Spinner(opts).spin(targetContent);
    //This variable decides when spinner loading for contentis closed.
    $scope.contentLoading = 1;
	
	var id = $routeParams.userId;	
	
	UserModerationService.getUser(id).then(
			function(result){
				$scope.user=result;				
				$scope.contentLoading--;
				console.log($scope.user.userRole[0].role);
			},
			function(error){					
				console.log(error)
				$scope.contentLoading--;
			}
		);
	
	$scope.deleteUser = function() {
   	 if (!confirm($filter('translate')("USER_DETAILED_CONFIRM_DELETE"))) {
            return;
        }
   	 
   	UserModerationService.deleteUser(id)
           .then(function(data) {
               $window.location.href = "#/ua/user/admin/users";
           },
           function(data) {
               $window.alert("Animal delete failed.");
           });
   }
	
	
	
	
}]);