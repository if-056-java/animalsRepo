angular.module('animalApp').factory('AuthenticationService',function (Base64, $q, $http, localStorageService, $location, $route, $window, $rootScope, RESOURCES){
	
	return {
		
		login:function (username, password){
			
			var authdata = Base64.encode(username + ':' + password);            
            
            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; 
                       
			var memoryMe = localStorageService.get("memoryMe");
			var def = $q.defer();
			
			$http.post(RESOURCES.LOGIN_BASIC + memoryMe, {})
	        .success(function(data){	        	        	
	        	if(data.userId==1){	
	        		def.resolve(data);
	        	} else {	        	
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
		        	
		        	def.resolve(data);		        	
	        	}
	        }) 
			.error(function(data, status){								
				def.reject("Failed to enter site");
			});
			return def.promise;
		},
		
		logout:function(){			
			
            $http.get(RESOURCES.LOGOUT)
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
		
			var def = $q.defer();
			
			$http.post(RESOURCES.REGISTRATION, user)
	        .success(function(data){
	        	def.resolve(data);	        	
	        }) 
			.error(function(data){
				def.reject("Failed to register user");
			});
			return def.promise;
		},
		
		confirmRegistration:function(userLogin,code){
			
			var def = $q.defer();
			
			$http.post(RESOURCES.CONFIRM_REGISTRATION + userLogin + "/" + code)
	        .success(function(data){ 
	        	def.resolve(data);
	        })
	        .error(function(data){
	        	def.reject("Failed to confirm Registration");
			});
			return def.promise;
		
		},
			
		
		refreshSession:function(){
									
			$http.get(RESOURCES.REFRESH)
	        .success(function(data){
	        	
	        	if(data.userId==0){
	        		console.log("Refresh Session eror");
	        		localStorageService.clearAll();
	        		$location.path("/ua/user/login");	
			        $route.reload();
			        $rootScope.errorMessage="Помилка входу. Параметри сесії на сервері задано невірно!";
	        	} else {        	
		        	localStorageService.cookie.set("accessToken",data.accessToken,30);	        	
		        	localStorageService.set("accessToken", data.accessToken);
		        	localStorageService.set("userId", data.userId);
		        	localStorageService.set("userName", data.socialLogin);
		        	localStorageService.set("userRole", data.userRole);
		        	localStorageService.set("userRoleId", data.userRoleId);	
		        	
		        	if(data.refreshGoogleToken !== "null"){
		        		localStorageService.set("refreshGoogleToken", data.refreshGoogleToken);	        	
		        		localStorageService.cookie.set("refreshGoogleToken",data.refreshGoogleToken,30);		        		
		        		localStorageService.set("disableGoogleButton", true);			        				        		
		        	}
		        	if(data.twitterToken !== "null"){
		        		localStorageService.cookie.set("twitterToken",data.twitterToken,30);
		        		localStorageService.cookie.set("twitterSecret",data.twitterSecret,30);		        		
		        		localStorageService.set("disableTwitterButton", true);		        		
		        	}
		        	if(data.facebookToken !== "null"){		        		
		        		localStorageService.set("disableFacebookButton", true);		        		
		        	}
		        	
		        	$location.path("/ua/user/profile");	
			        $route.reload();
	        	}
	        	
	        }) 
			.error(function(data){
				localStorageService.set("userId", null);
				console.log("refresh session error");
				localStorageService.clearAll();
				$location.path("/ua");	
		        $route.reload();
			});			
			       	
		}			
		
	};	
	
});