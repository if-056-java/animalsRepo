//created by 41X
angular.module('animalApp').factory('userData',function ($http, $rootScope, $location, userAccount, localStorageService){
	
	return {
		
		getUser:function (id) {	
			$http.defaults.headers.common['AccessToken'] = localStorageService.get("accessToken");
			return $http.get("/webapi/users/user/" + id);		
		},		
	
		getUserAnimals: function (id) {
			return $http.get("/webapi/users/user/"+id+"/animals")			
		},
		

		updateUser:function (user , id){
			$http.put("/webapi/users/user/" + id, user)
	        .success(function(data){
	        	console.log("user updated");
	        	console.log(data);	        		        
	        }); 			
		},			
		
		
	};	
	
});