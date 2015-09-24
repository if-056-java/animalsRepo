var animalAppControllers = angular.module('UserEditorAdminController', ['DPController']);

animalApp.controller('UserEditorAdminController', ['$scope', 'UserModerationService', 'localStorageService', '$routeParams', '$window', '$filter',                                               
                                               function($scope, UserModerationService, localStorageService, $routeParams, $window, $filter) {
	
	//initialize loading spinner
    var targetContent = document.getElementById('loading-block');
    new Spinner(opts).spin(targetContent);
    //This variable decides when spinner loading for contentis closed.
    $scope.contentLoading = 1;
    
    $scope.currentLanguage = localStorage.getItem("NG_TRANSLATE_LANG_KEY");
	
	var id = $routeParams.userId;	
	
	UserModerationService.getUser(id).then(
			function(result){
				$scope.user=result;				
				$scope.contentLoading--;				
			},
			function(error){					
				console.log(error)
				$scope.contentLoading--;
			}
		);
	
	$scope.updateUser = function() {
   	    	 
   	UserModerationService.updateUser($scope.user, id)
           .then(function(data) {
               $window.location.href = "#/ua/user/admin/users/" + id;
           },
           function(error) {
               $window.alert("Animal update failed.");
           });
   }
	
	
	
	
}]);