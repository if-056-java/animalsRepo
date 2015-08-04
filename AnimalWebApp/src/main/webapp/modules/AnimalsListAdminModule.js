angular.module('AnimalsListAdminService', [])
    .service('AnimalsListAdminService', ['$http', '$q', function($http, $q) {
        
        this.getAnimals = function(page, limit) {
            var def = $q.defer();

            $http.get("/AnimalWebApp/webapi/adm_animals_list/" + page + "/" + limit)
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get albums");
                });
            
            return def.promise;    
        }
        
        this.getPagesCount = function(limit) {
            var def = $q.defer();

            $http.get("/AnimalWebApp/webapi/adm_animals_list/pagenator")
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get page count");
                });
            
            return def.promise;    
        }
    }]);