//created by 41X
angular.module('animalApp').factory('userAccount',function (Base64, $http, $rootScope, $location){
	
	return {
		
		login:function (username, password){
			
			var authdata = Base64.encode(username + ':' + password);
			console.log(username);
			console.log(password);
			console.log(authdata);

            $rootScope.globals = {
                currentUser: {
                    username: username,
                    id:password,  				//TEMPORARY Remove when real authorization
                    authdata: authdata
                }
            };
            
            console.log($rootScope.globals.currentUser.username+ " - rootscope username");
            console.log($rootScope.globals.currentUser.id + " - rootscope id");
            
            
            
            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            //$cookieStore.put('globals', $rootScope.globals); 
            
			
			$http.post("/webapi/account/login", {})
	        .success(function(data){
	        	$rootScope.sessionId=data;
		        console.log("inside auth. success. Session Id - " + $rootScope.sessionId);
		        $location.path("/ua/user/profile");	        
	        }) 
			.error(function(data){
				console.log("zrada");
			});
		},
		
		logout:function(){
			
			$rootScope.globals = {};
            //$cookieStore.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic ';
            $location.path("/ua");	
		}
		
	};	
	
});