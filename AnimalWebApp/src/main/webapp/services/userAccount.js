//created by 41X
angular.module('animalApp').factory('userAccount',function (Base64, $http, localStorageService, $location, $route){
	
	return {
		
		login:function (username, password){
			
			var authdata = Base64.encode(username + ':' + password);            
            
            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; 
                       
			var memoryMe = localStorageService.get("memoryMe");
            
			$http.post("/webapi/account/login/" + memoryMe, {})
	        .success(function(data){
	        	
	        	if (localStorageService.get("memoryMe")=="ON"){
	        		localStorageService.cookie.set("accessToken",data.accessToken,30);	        		
	        	} else {
	        		localStorageService.cookie.set("accessToken",data.accessToken,0.065);	//90 min
	        	}
	        	
	        	localStorageService.set("accessToken", data.accessToken);
	        	localStorageService.set("userId", data.userId);
	        	localStorageService.set("userName", data.socialLogin);
	        	localStorageService.set("userRole", data.userRole);
	        	localStorageService.set("userRoleId", data.userRoleId);
	        	
		        $location.path("/ua/user/profile");	
		        $route.reload();		       
	        }) 
			.error(function(data, status){
				console.log("zrada");
				console.log(status);
			});
		},
		
		logout:function(){			
			
            $http.get("/webapi/account/logout")
	        .success(function(data){
	        	
	        	localStorageService.clearAll();	
		        $location.path("/ua");	
		        $route.reload();		      
		        
	        }) 
			.error(function(data){				
				console.log("logout session error");
				localStorageService.clearAll();	
		        $location.path("/ua");	
		        $route.reload();
			});
            
		},
		
		
		registerUser:function(user){			
		
			
			$http.post("/webapi/account/registration", user)
	        .success(function(data){
	        	if(data.userId==0){
	        		console.log("Registration error. SocialLogin is already exist");	        		
	        	} else {
	        		
	        		if (localStorageService.get("memoryMe")=="ON"){
		        		localStorageService.cookie.set("accessToken",data.accessToken,30);	        		
		        	} else {
		        		localStorageService.cookie.set("accessToken",data.accessToken,0.065);
		        	}
		        	
		        	localStorageService.set("accessToken", data.accessToken);
		        	localStorageService.set("userId", data.userId);
		        	localStorageService.set("userName", data.socialLogin);
		        	localStorageService.set("userRole", data.userRole);
		        	localStorageService.set("userRoleId", data.userRoleId);
		        	
			        $location.path("/ua/user/profile");	
			        $route.reload();
	        	}		        
	        }) 
			.error(function(data){				
				console.log("registration error");
			});
		},
		
		refreshSession:function(){
									
			$http.get("/webapi/account/refresh")
	        .success(function(data){
	        	
	        	localStorageService.cookie.set("accessToken",data.accessToken,30);	        	
	        	localStorageService.set("accessToken", data.accessToken);
	        	localStorageService.set("userId", data.userId);
	        	localStorageService.set("userName", data.socialLogin);
	        	localStorageService.set("userRole", data.userRole);
	        	localStorageService.set("userRoleId", data.userRoleId);	
	        	
	        	$location.path("/ua/user/profile");	
		        $route.reload();
	        	
	        }) 
			.error(function(data){
				localStorageService.set("userId", null);
				console.log("refresh session error");
				$location.path("/ua");	
		        $route.reload();
			});			
			       	
		}
		
	};	
	
});