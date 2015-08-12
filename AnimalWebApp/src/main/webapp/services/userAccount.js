//created by 41X
angular.module('animalApp').factory('userAccount',function (Base64, $http, $rootScope, $location){
	
	return {
		
		login:function (username, password){
			
			var authdata = Base64.encode(username + ':' + password);

            $rootScope.globals = {
                currentUser: {
                    username: username,
                    //id:password,  				//TEMPORARY Remove when real authorization
                    authdata: authdata
                }
            };
            
            console.log("rootScope");
            //console.log($rootscope.currentUser.username);

            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            //$cookieStore.put('globals', $rootScope.globals); 
            
			
			$http.post("/webapi/account/login", {})
	        .success(function(data){	        	
		        console.log("inside auth. success");
		        $location.path("/ua");		        
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