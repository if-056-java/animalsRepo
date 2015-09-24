angular.module('animalApp').factory('UserModerationService',function ($q, $http, $rootScope, $location, localStorageService, RESOURCES){
	
	return {
		
		getUser:function (id) {	
			
			var def = $q.defer();
			
			$http.defaults.headers.common['AccessToken'] = localStorageService.get("accessToken");
			$http.get(RESOURCES.USER_FOR_ADMIN + id)
			.success(function (data) {
                def.resolve(data);
            })
            .error(function (error) {
                def.reject("Failed to get user");
            });
			return def.promise;
		},	

		updateUser:function (user, id){
			
			var def = $q.defer();
			
			$http.put(RESOURCES.USER_FOR_ADMIN + id, user)
			.success(function (data) {
                def.resolve(data);
            })
            .error(function (error) {
                def.reject("Failed to update user");
            });
			return def.promise;			
		},
		
				
		deleteUser: function (id) {
			
			var def = $q.defer();			
						
			$http.delete(RESOURCES.USER_FOR_ADMIN + id)
			.success(function (data) {
                def.resolve(data);
            })
            .error(function (error) {
                def.reject("Failed to delete animal");
            });
			return def.promise;
		}		     
		
	};	
	
});