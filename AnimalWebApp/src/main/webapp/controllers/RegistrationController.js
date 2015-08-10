var animalAppControllers = angular.module('RegistrationController', []);

animalApp.controller('RegistrationController', function($scope, $location, $http, $rootScope) {
	
		$scope.submitRegForm=function(){
			
			        		
			$scope.fields.active = true; 
			$scope.fields.userRole = {"id": 3};
			$scope.fields.userType = {"id": 1};
			
			var d = new Date();
			var month = d.getMonth()+1;
			var day = d.getDate();
			var regDate = d.getFullYear() + '-' + (month<10 ? '0' : '') + month + '-' +    (day<10 ? '0' : '') + day;
			$scope.fields.registrationDate = regDate;
			
			$scope.fields.password=CryptoJS.MD5($scope.fields.password).toString();			
			
	        var data=$scope.fields; 
	        console.log(data);
			
	        $http.post("/webapi/users/user", data)
	        .success(function(data){
	        	console.log(data);
	        	console.log(data.id);
	        	$rootScope.id =data.id;
	        	$location.path("/ua/user/profile");
	        });   
	        
	       
	        
		
		};               
        
});

