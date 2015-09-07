//created by 41X
angular.module('animalApp').factory('userAccount',function (Base64, $http, localStorageService, $location, $route, $window, $rootScope){
	
	return {
		
		login:function (username, password){
			
			var authdata = Base64.encode(username + ':' + password);            
            
            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; 
                       
			var memoryMe = localStorageService.get("memoryMe");
            
			$http.post("/webapi/account/login/" + memoryMe, {})
	        .success(function(data){
	        		        	
	        	if(data.userId==1){
	        		$rootScope.errorMessage="Для входу потрібно підтвердити реєстрацію!";
	        		console.log("Confirm registration with email");
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
		        	
		        	
			        $location.path("/ua/user/profile");	
			        $route.reload();
	        	}
	        }) 
			.error(function(data, status){
				console.log("zrada");
				console.log(status);
				$rootScope.errorMessage="Помилка входу. Перевірте свої дані!";
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
	        		$rootScope.errorRegistrationMessage="Помилка реєстрації. Даний логін вже використовується!";
	        		console.log("Registration error. SocialLogin is already exist");
	        	} else if (data.userId==1) {	        		
			        $location.path("/ua/user/confirmRegistration");	
			        $route.reload();
	        	} else {
	        		console.log("error");
	        	}
	        	
	        }) 
			.error(function(data){				
				console.log("registration error");
				$rootScope.errorRegistrationMessage="Помилка реєстрації! Спробуйте ще раз";
			});
		},
		
		confirmRegistration:function(userLogin,code){
			
			$http.post("/webapi/account/confirmRegistration/" + userLogin + "/" + code)
	        .success(function(data){
	        		        	
	        	localStorageService.cookie.set("accessToken",data.accessToken,0.065);  	
	        	
	        	localStorageService.set("accessToken", data.accessToken);
	        	localStorageService.set("userId", data.userId);
	        	localStorageService.set("userName", data.socialLogin);
	        	localStorageService.set("userRole", data.userRole);
	        	localStorageService.set("userRoleId", data.userRoleId);
	        	
		        $location.path("/ua/user/profile");	
		        $route.reload();
	        })
	        .error(function(data){				
				console.log("registration error");
				$rootScope.errorRegistrationConfirmMessage="Помилка реєстрації! Спробуйте ще раз";
			});
		
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
		        	}
		        	if(data.twitterToken !== "null"){
		        		localStorageService.cookie.set("twitterToken",data.twitterToken,30);
		        		localStorageService.cookie.set("twitterSecret",data.twitterSecret,30);
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
			
			console.log("loginGoogle");
			
			$http.get("/webapi/account/login/google")
			.success(function(data){
				console.log("success not direct");
				$window.location.href = (data);				
			})
			.error(function(data){
				console.log("error not direct");
			})
			
		},
		
		loginDirectGoogle:function(){
			
			console.log("loginDirectGoogle");
			
			var refreshToken = localStorageService.cookie.get("refreshGoogleToken");			
			console.log(refreshToken);			
			
			
			$http.get("/webapi/account/login/google_login_direct", {params:{code:refreshToken}})
			.success(function(data){
				console.log("success direct");
				$window.location.href = (data);
			})
			.error(function(data){
				console.log("error direct. Maybe RefreshToken expired");
				$rootScope.errorMessage="Помилка входу. Термін дії GoogleRefreshToken закінчився! Спробуйте ще раз!";
				localStorageService.cookie.remove("refreshGoogleToken");
			})
		},
		
		
		loginFacebook:function(){
			
			console.log("loginFacebook");
			
			$http.get("/webapi/account/login/facebook")
			.success(function(data){
				console.log("success not direct");
				$window.location.href = (data);				
			})
			.error(function(data){
				console.log("error not direct");
			})
			
		},
		

		loginTwitter:function(){
			
			console.log("loginTwitter");
			
			$http.get("/webapi/account/login/twitter")
			.success(function(data){
				console.log("success not direct");
				$window.location.href = (data);				
			})
			.error(function(data){
				console.log("error not direct");
			})
			
		},
		
		loginDirectTwitter:function(){
			
			console.log("loginDirectTwitter");
			
			var twitterToken = localStorageService.cookie.get("twitterToken");
			var twitterSecret = localStorageService.cookie.get("twitterSecret");				
			
			
			$http.get("/webapi/account/login/twitter_login_direct", {params:{token:twitterToken, secret:twitterSecret}})
			.success(function(data){
				console.log("success direct");
				$window.location.href = (data);
			})
			.error(function(data){
				console.log("error direct. Maybe aToken expired");
				$rootScope.errorMessage="Помилка входу. Спробуйте ще раз!";
				localStorageService.cookie.remove("twitterToken");
				localStorageService.cookie.remove("twitterSecret");				
			})
		},
		
		
		
	};	
	
});