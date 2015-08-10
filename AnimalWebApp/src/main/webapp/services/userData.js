//created by 41X
angular.module('animalApp').factory('userData',function ($http, $rootScope, $location){
	
	return {
		getUser: function (id) {
			return $http.get("/webapi/users/user/" + id);
			
		},		
	
		createUser:function (user){
			$http.post("/webapi/users/user", user)
	        .success(function(data){
	        	console.log(data);
	        	console.log(data.id);
		        $rootScope.id =data.id;	
		        console.log("id rootscope - " + $rootScope.id);
		        $location.path("/ua/user/profile");		        
	        }); 			
		}
		
	};	
	
});