var animalAppControllers = angular.module('UserEditorAdminController', ['DPController']);

animalApp.controller('UserEditorAdminController', ['$scope', 'UserModerationService', 'localStorageService', '$routeParams',
                                                   '$window', '$filter', '$location',                                              
                                               function($scope, UserModerationService, localStorageService, $routeParams,
                                            		   $window, $filter, $location) {
	
	//initialize loading spinner
    var targetContent = document.getElementById('loading-block');
    new Spinner(opts).spin(targetContent);

    $scope.errorMessage = '';
    //This variable decides when spinner loading for content is closed.
    $scope.contentLoading = 1; 
    
    if (localStorageService.get('userRole')!=="moderator"){
        $location.path("#ua");	
	}
	
	var id = $routeParams.userId;	
	
	UserModerationService.getUser(id).then(
			function(result){
				$scope.user=result;				
				$scope.contentLoading--;
				if($scope.user.isActive){ 
						$scope.active="true";
					} else {
						$scope.active="false";
					}
				;
			},
			function(error){
                $scope.errorMessage = error;
				$scope.contentLoading--;
			}
		);
	
	$scope.updateUser = function() {
		
		if($scope.user.userRole[0].role=="moderator")$scope.user.userRole[0].id=1;
		if($scope.user.userRole[0].role=="guest")$scope.user.userRole[0].id=3;
		if($scope.user.userRole[0].role=="doctor")$scope.user.userRole[0].id=4;

		if($scope.user.userType.type=="owner")$scope.user.userType.id=1;
		if($scope.user.userType.type=="vet")$scope.user.userType.id=2;
		if($scope.user.userType.type=="cynologist union")$scope.user.userType.id=3;
		if($scope.user.userType.type=="organization")$scope.user.userType.id=4;
		if($scope.user.userType.type=="misc")$scope.user.userType.id=5;
		
		if($scope.active=="true")$scope.user.isActive=true;
		if($scope.active=="false")$scope.user.isActive=false;
   	    	 
	   	UserModerationService.updateUser($scope.user, id)
	           .then(function(result) {	        	   
	        	   if(result.userId==0){
	        		   $scope.errorUpdateMessage=true;
	        	   } else{
	        		   $window.location.href = "#/ua/user/admin/users/" + id;	        		   
	        	   }
	           },
	           function(error) {
	               $window.alert("User update failed.");
	           });
   }
	
	
	
}]);