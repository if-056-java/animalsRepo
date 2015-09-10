angular.module('animalApp').factory('userData',function ($q, $http, $rootScope, $location, userAccount, localStorageService){
	
	return {
		
		getUser:function (id) {	
			
			var def = $q.defer();
			
			$http.defaults.headers.common['AccessToken'] = localStorageService.get("accessToken");
			$http.get("/webapi/users/user/" + id)
			.success(function (data) {
                def.resolve(data);
            })
            .error(function (error) {
                def.reject("Failed to get user");
            });
			return def.promise;
		},		
	
		getUserAnimals: function (id) {
			
			var def = $q.defer();
			
			$http.get("/webapi/users/user/"+id+"/animals")
			.success(function (data) {
                def.resolve(data);
            })
            .error(function (error) {
                def.reject("Failed to get animals");
            });
			return def.promise;
		},
		
		getPaginator: function(id){
			var def = $q.defer();
			$http.get("/webapi/users/user/"+id+"/animals/paginator")
			.success(function (data) {
                def.resolve(data);
            })
            .error(function (error) {
                def.reject("Failed to get animals");
            });
			return def.promise;
		},
		
		getUserAnimalsWithFilter: function(id, filter){
			var def = $q.defer();
			
			$http.post("/webapi/users/user/"+id+"/animals", filter)
			.success(function (data) {
                def.resolve(data);
            })
            .error(function (error) {
                def.reject("Failed to get animals");
            });
			return def.promise;
		},
		

		updateUser:function (user , id){
			
			var def = $q.defer();
			
			$http.put("/webapi/users/user/" + id, user)
			.success(function (data) {
                def.resolve(data);
            })
            .error(function (error) {
                def.reject("Failed to update user");
            });
			return def.promise;			
		},
		
		getAnimal: function (animalId) {
			
			var def = $q.defer();
			var id = localStorageService.get("userId");
			
			$http.defaults.headers.common['AccessToken'] = localStorageService.get("accessToken");
			$http.get("/webapi/users/user/"+id+"/animals/"+ animalId)
			.success(function (data) {
                def.resolve(data);
            })
            .error(function (error) {
                def.reject("Failed to get animal");
            });
			return def.promise;
		},	
		
		
		deleteAnimal: function (animalId) {
			
			var def = $q.defer();
			var id = localStorageService.get("userId");
						
			$http.delete("/webapi/users/user/"+id+"/animals/"+ animalId)
			.success(function (data) {
                def.resolve(data);
            })
            .error(function (error) {
                def.reject("Failed to delete animal");
            });
			return def.promise;
		},
		
		updateAnimal: function(animal) {
            var def = $q.defer();
            var id = localStorageService.get("userId");
            
            $http.defaults.headers.common['AccessToken'] = localStorageService.get("accessToken");
            $http.post("/webapi/users/user/"+id+"/animals/animal", animal)
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to update animal.");
                });

            return def.promise;
        }
		
	};	
	
});