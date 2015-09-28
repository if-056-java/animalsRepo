var animalAppControllers = angular.module('UserDetailedAdminController', []);

animalApp.controller('UserDetailedAdminController', ['$scope', 'UserModerationService', 'localStorageService', '$routeParams',
                                                     '$window', '$filter', '$location',                                               
                                               function($scope, UserModerationService, localStorageService, $routeParams,
                                            		   $window, $filter, $location) {
	
	//initialize loading spinner
    var targetContent = document.getElementById('loading-block');
    new Spinner(opts).spin(targetContent);

    $scope.errorMessage = '';
    //This variable decides when spinner loading for contentis closed.
    $scope.contentLoading = 1;
    
    if (localStorageService.get('userRole')!=="moderator"){
    	$location.path("#ua");	
	}
    
    $scope.currentLanguage = localStorage.getItem("NG_TRANSLATE_LANG_KEY");
	
	var id = $routeParams.userId;	
	
	UserModerationService.getUser(id).then(
			function(result){
				$scope.user=result;				
				$scope.contentLoading--;				
			},
			function(error){
                $scope.errorMessage = error;
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