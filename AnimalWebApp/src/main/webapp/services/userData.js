//created by 41X
angular.module('animalApp').factory('userData',function ($http, $rootScope, $location, userAccount){
	
	return {
		
		getUser:function (id) {			
			return $http.get("/webapi/users/user/" + id);		
		},		
	
		createUser:function (user){
			$http.post("/webapi/users/user", user)
	        .success(function(data){	        	
		        var id = data.id;
		        var name = data.name;
		        console.log("id rootscope - " + id);
		        //userAccount.login(name, id)
		        $location.path("/ua/user/profile");	
		        //$route.reload();
	        }); 			
		},
		
		updateUser:function (user , id){
			$http.put("/webapi/users/user/" + id, user)
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