var animalAppControllers = angular.module('UserProfileController', []);

animalApp.controller('UserProfileController', function($scope, $http) {
	
	$scope.IsHidden = true;
	$scope.showPopup = function () {$scope.IsHidden =  false;}    
	$scope.closePopup = function () {$scope.IsHidden =  true;}
	
	$scope.setId=function(){
	
		$scope.userInfo = null;
		$scope.fields = null;
		var id = $scope.set.id;
		console.log(id);
		
		$http.get("/webapi/users/user/" + id)				//webapi/users/user/{id}
	    .success(function(data) {
	    	$scope.userInfo = data;
	    	$scope.fields=$scope.userInfo;
	    	console.log(data);
	    })
	    
	    $http.get("/webapi/users/user/"+id+"/animals")  	//webapi/users/user/{id}/animals
	    .success(function(data) {
	    	$scope.userAnimalInfo = data;
	    	console.log(data);
	    })
	};
    
       
         
    $scope.submitUpdateForm=function(){    	
    	
    	$scope.fields.password=CryptoJS.MD5($scope.fields.passwordNew).toString();
    	
		var data=$scope.fields; 
        console.log(data);
		
        $http.put("/webapi/users/user", data)
        .success(function(data){
        	console.log(data);      
        });  
	
	};  
	
});