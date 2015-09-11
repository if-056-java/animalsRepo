angular.module('animalApp').factory('OauthAuthenticationService',function ($http, localStorageService, $window, $rootScope){
	
	return {
		
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