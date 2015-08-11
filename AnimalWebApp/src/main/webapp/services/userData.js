//created by 41X
angular.module('animalApp').factory('userData',function ($http, $rootScope, $location){
	
	return {
		
		getUser:function (id) {			
			return $http.get("/webapi/users/user/" + id);		
		},		
	
		createUser:function (user){
			$http.post("/webapi/users/user", user)
	        .success(function(data){	        	
		        $rootScope.id =data.id;	
		        console.log("id rootscope - " + $rootScope.id);
		        $location.path("/ua/user/profile");		        
	        }); 			
		},
		
		updateUser:function (user){
			$http.put("/webapi/users/user", user)
	        .success(function(data){
	        	console.log("user update");
	        	console.log(data);	        		        
	        }); 			
		},			
		
		getUserAnimals: function (id) {
			return $http.get("/webapi/users/user/"+id+"/animals")			
		}
		
	};	
	
});