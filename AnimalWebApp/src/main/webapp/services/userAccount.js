angular.module('animalApp').factory('userAccount',function (Base64, $q, $http, localStorageService, $location, $route, $window, $rootScope){
	
	return {
		
		login:function (username, password){
			
			var authdata = Base64.encode(username + ':' + password);            
            
            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; 
                       
			var memoryMe = localStorageService.get("memoryMe");
			var def = $q.defer();
			
			$http.post("/webapi/account/login/" + memoryMe, {})
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
		
			var def = $q.defer();
			
			$http.post("/webapi/account/registration", user)
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
			
			$http.post("/webapi/account/confirmRegistration/" + userLogin + "/" + code)
	        .success(function(data){ 
	        	def.resolve(data);
	        })
	        .error(function(data){
	        	def.reject("Failed to confirm Registration");
			});
			return def.promise;
		
		},
			
		
		refreshSession:function(){
									
			$http.get("/webapi/account/refresh")
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
			       	
		},
		
		loginGoogle:function(){		
		
			$http.get("/webapi/account/login/google")
			.success(function(data){			
				$window.location.href = (data);				
			})
			.error(function(data){
				console.log("error not direct");
			})
			
		},
		
		loginDirectGoogle:function(){
			
			
			var refreshToken = localStorageService.cookie.get("refreshGoogleToken");			
						
			$http.get("/webapi/account/login/google_login_direct", {params:{code:refreshToken}})
			.success(function(data){			
				$window.location.href = (data);
			})
			.error(function(data){
				console.log("error direct. Maybe RefreshToken expired");
				$rootScope.errorMessage="Помилка входу. Термін дії GoogleRefreshToken закінчився! Спробуйте ще раз!";
				localStorageService.cookie.remove("refreshGoogleToken");
			})
		},
		
		
		loginFacebook:function(){
			
			$http.get("/webapi/account/login/facebook")
			.success(function(data){				
				$window.location.href = (data);				
			})
			.error(function(data){
				console.log("error not direct");
			})
			
		},
		

		loginTwitter:function(){
						
			$http.get("/webapi/account/login/twitter")
			.success(function(data){				
				$window.location.href = (data);				
			})
			.error(function(data){
				console.log("error not direct");
			})
			
		},
		
		loginDirectTwitter:function(){			
			
			var twitterToken = localStorageService.cookie.get("twitterToken");
			var twitterSecret = localStorageService.cookie.get("twitterSecret");
			
			$http.get("/webapi/account/login/twitter_login_direct", {params:{token:twitterToken, secret:twitterSecret}})
			.success(function(data){				
				$window.location.href = (data);
			})
			.error(function(data){
				console.log("error direct. Maybe aToken expired");
				$rootScope.errorMessage="Помилка входу. Спробуйте ще раз!";
				localStorageService.cookie.remove("twitterToken");
				localStorageService.cookie.remove("twitterSecret");				
			})
		}	
		
	};	
	
});