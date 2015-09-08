//created by 41X
var animalAppControllers = angular.module('UserProfileController', []);

animalApp.controller('UserProfileController', ['$scope', 'userData', 'userAccount', 'hashPassword', 'localStorageService', '$route', '$location',
                                               function($scope, userData, userAccount, hashPassword, localStorageService, $route, $location) {
		
	//initialize loading spinner
    var targetContent = document.getElementById('loading-block');
    var targetContent2 = document.getElementById('loading-block2');
    new Spinner(opts).spin(targetContent);
    new Spinner(opts).spin(targetContent2);
    $scope.contentLoading = 0;
    $scope.contentLoading2 = 0;
	
    if(localStorageService.get("disableGoogleButton")){
    	$scope.disableGoogle=true;
    }
    if(localStorageService.get("disableTwitterButton")){
    	$scope.disableTwitter=true;
    }
    if(localStorageService.get("disableFacebookButton")){
    	$scope.disableFacebook=true;
    }
	
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
		$scope.contentLoading++;
		userData.getUser(id).then(
				function(result){
					$scope.userInfo=result;
					$scope.fields = $scope.userInfo;
					$scope.contentLoading--;
				},
				function(error){					
					console.log(error)
					$scope.contentLoading--;
				}
			);
		
//		userData.getUserAnimals(id).success(function(data){			//old school
//			$scope.userAnimalInfo=data;
//		});
		
		$scope.contentLoading2++;
		userData.getUserAnimals(id).then(
				function(result){
					$scope.userAnimalInfo=result;
					$scope.contentLoading2--;
				},
				function(error){
					console.log(error)
					$scope.contentLoading2--;
				}
		);		
	}
		
	
	
	
    $scope.submitUpdatedForm=function(){  
    	
    	
    	if ($scope.passwordNew!=$scope.password_new_confirm){
			$scope.errorConfirmMessage=true;				
		} else {				
		   	
    	if($scope.passwordNew){    		
    		$scope.fields.password=hashPassword($scope.passwordNew);     		
    	}     	
		userData.updateUser($scope.fields, $scope.fields.id).then(
				function(result){
					console.log("user updated");				
				},
				function(error){
					console.log(error)
				}
		);		
		$scope.IsHidden =  true;
		} 
	
	};	  
	
	$scope.AddOwnAnimal=function(){    	
    	
    	if($scope.userInfo.name=="unknown" || $scope.userInfo.surname=="N/A" || $scope.userInfo.address=="N/A" ||
    			$scope.userInfo.email =="N/A" || $scope.userInfo.phone =="N/A"){    		
    		$scope.errorAddAnimalMessage="Помилка. Відсутні контактні дані користувача! Відредагуййте профіль користувача";   		
    	} else {
    		$location.path("/ua/animal/registration_owned");	    		
    	}  	
	};	
	
	$scope.JoinGoogle=function(){	
		
		userAccount.loginGoogle();
		
		
	};
	
	$scope.JoinFacebook=function(){	
		
		userAccount.loginFacebook();
		
		
	};
	
	$scope.JoinTwitter=function(){	
		
		userAccount.loginTwitter();
		
		
	};
	
	if($location.search().join){
		$scope.errorJoinMessage="Помилка об'єднання акаунтів. Даний соціальний акаунт вже використовується!"; 
	} else {
		$scope.errorJoinMessage=null;
	}
	
	
}]);