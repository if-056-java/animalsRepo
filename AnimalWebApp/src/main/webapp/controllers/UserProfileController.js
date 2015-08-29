//created by 41X
var animalAppControllers = angular.module('UserProfileController', []);

animalApp.controller('UserProfileController', ['$scope', 'userData', 'userAccount', 'hashPassword', 'localStorageService', '$route', '$location', '$rootScope',
                                               function($scope, userData, userAccount, hashPassword, localStorageService, $route, $location, $rootScope) {
		
		
	$scope.IsHidden = true;
	$scope.showPopup = function () {$scope.IsHidden =  false;}    
	$scope.closePopup = function () {$scope.IsHidden =  true; $route.reload();}
	
	$scope.userInfo = null;
	$scope.fields = null;
	
	if(!localStorageService.get("userId")){
		userAccount.refreshSession();  		
	} else {
		
		var id = localStorageService.get("userId");
		
//		userData.getUser(id).success(function(data){				//old school
//			$scope.userInfo=data;
//			$scope.fields = $scope.userInfo;
//		}) 	
		
		userData.getUser(id).then(
				function(result){
					$scope.userInfo=result;
					$scope.fields = $scope.userInfo;					
				},
				function(error){
					//$scope.errorMessage = error;
					console.log(error)
				}
			);
		
//		userData.getUserAnimals(id).success(function(data){			//old school
//			$scope.userAnimalInfo=data;
//		});
		
		userData.getUserAnimals(id).then(
				function(result){
					$scope.userAnimalInfo=result;				
				},
				function(error){
					console.log(error)
				}
		);		
	}
		
	
	
	
    $scope.submitUpdatedForm=function(){    	
    	
    	if($scope.passwordNew){    		
    		$scope.fields.password=hashPassword($scope.passwordNew);     		
    	}     	
    	
		//userData.updateUser($scope.fields, $scope.fields.id); 		//old school
		
		userData.updateUser($scope.fields, $scope.fields.id).then(
				function(result){
					console.log("user updated");				
				},
				function(error){
					console.log(error)
				}
		); 	
		
		$scope.IsHidden =  true;
	
	};	  
	
	$scope.AddOwnAnimal=function(){    	
    	
    	if($scope.userInfo.name=="unknown" || $scope.userInfo.surname=="N/A" || $scope.userInfo.address=="N/A" ||
    			$scope.userInfo.email =="N/A" || $scope.userInfo.phone =="N/A"){    		
    		$rootScope.errorAddAnimalMessage="Помилка. Відсутні контактні дані користувача! Відредагуййте профіль користувача";   		
    	} else {
    		$location.path("/ua/animal/registration_owned");	    		
    	}  	
	};	
	
	$scope.JoinGoogle=function(){	
		
		userAccount.loginGoogle();
		
		
	};
	
	if($location.search().join){
		$rootScope.errorJoinMessage="Помилка об'єднання акаунтів. Даний соціальний акаунт вже використовується!"; 
	} else {
		$rootScope.errorJoinMessage=null;
	}
	
	
}]);