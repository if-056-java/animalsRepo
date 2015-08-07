angular.module('AnimalsListAdminService', [])
    .service('AnimalsListAdminService', ['$http', '$q', function($http, $q) {
        
        this.getAnimals = function(page, limit) {
            var def = $q.defer();

            $http.get("/webapi/user/home/animals/" + page + "/" + limit)
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get animals");
                });
            
            return def.promise;    
        }
        
        this.getPagesCount = function(limit) {
            var def = $q.defer();

            $http.get("/webapi/user/home/animals/pagenator")
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get page count");
                });
            
            return def.promise;    
        }

        this.getAnimal = function(animalId) {
            var def = $q.defer();

            $http.get("/webapi/user/home/animals/" + animalId)
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get animal");
                });

            return def.promise;
        }

        this.deleteAnimal = function(animalId) {
            var def = $q.defer();

            $http.delete("/webapi/user/home/animals/" + animalId)
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to delete animal");
                });

            return def.promise;
        }

        this.updateAnimal = function(animal) {
            var def = $q.defer();

            $http.post("/webapi/user/home/animals/editor", animal)
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to delete animal");
                });

            return def.promise;
        }
    }]);