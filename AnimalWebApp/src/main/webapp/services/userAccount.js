//created by 41X
angular.module('animalApp').factory('userAccount',function (Base64, $http, $rootScope, $location, $route){
	
	return {
		
		login:function (username, password){
			
			var authdata = Base64.encode(username + ':' + password);
			console.log(username);
			console.log(password);
			console.log(authdata);

            $rootScope.globals = {
                currentUser: {
                    username: username,                    		
                    authdata: authdata
                }
            };
            
            console.log($rootScope.globals.currentUser.username+ " - rootscope username");
            console.log($rootScope.globals.currentUser.authdata + " - rootscope authdata");
            
            
            
            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            //$cookieStore.put('globals', $rootScope.globals); 
            
			
			$http.post("/webapi/account/login", {})
	        .success(function(data){
	        	
	        	$rootScope.sessionId=data.sessionId;
	        	$rootScope.userName=data.userName;
	        	$rootScope.userSurname=data.userSurname;
	        	$rootScope.userId=data.userId;
	        	$rootScope.socialLogin=data.socialLogin;
	        	$rootScope.userRole=data.userRole;
	        	//$rootScope.userType=data.userId;
	        	
	        	
		        console.log("inside auth. success. Session Id - " + $rootScope.sessionId);
		        console.log(" UserName - " + $rootScope.userName);
		        console.log(" userId - " + $rootScope.userId);
		        
		        $location.path("/ua/user/profile");	
		        $route.reload();
		        //$window.location.reload();
	        }) 
			.error(function(data, status){
				console.log("zrada");
				console.log(status);
			});
		},
		
		logout:function(){
			
			console.log("logout");
			
			$rootScope.globals = {};
            //$cookieStore.remove('globals');
            //$http.defaults.headers.common.Authorization = 'Basic ';
			
            $http.get("/webapi/account/logout")
	        .success(function(data){	        	
	        	$rootScope.userId=data.userId;
	        	$rootScope.userName=null;
		        console.log("Logout. success. Session Id - " + $rootScope.userId);	
		        $location.path("/ua");
		        $route.reload();
	        }) 
			.error(function(data){
				$rootScope.sessionId=null;
				console.log("logout session error");
			});  
		},
		
		refreshSession:function(){
			
			console.log("restart Session");
			
			$http.get("/webapi/account/refresh")
	        .success(function(data){
	        	$rootScope.sessionId=data.sessionId;
	        	$rootScope.userName=data.userName;
	        	$rootScope.userId=data.userId;
		        console.log("Restart. success. Session Id - " + $rootScope.sessionId);		                
	        }) 
			.error(function(data){
				$rootScope.sessionId=null;
				console.log("restart session error");
			});
		},
		
		registerUser:function(user){
			
			console.log("registration");
			
			$http.post("/webapi/account/registration", user)
	        .success(function(data){
	        	if(data.userId==0){
	        		console.log("Registration error. SocialLogin is already exist");	        		
	        	} else {
		        	$rootScope.sessionId=data.sessionId;
		        	$rootScope.userName=data.userName;
		        	$rootScope.userSurname=data.userSurname;
		        	$rootScope.userId=data.userId;
		        	$rootScope.socialLogin=data.socialLogin;
		        	$rootScope.userRole=data.userRole;
		        	
			        console.log("Registration success. Session Id - " + $rootScope.userId);	
			        $location.path("/ua/user/profile");	
			        $route.reload();
	        	}		        
	        }) 
			.error(function(data){
				$rootScope.sessionId=null;
				console.log("registration error");
			});
		}
		
	};	
	
});