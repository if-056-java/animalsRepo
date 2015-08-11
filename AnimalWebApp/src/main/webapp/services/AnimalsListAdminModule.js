angular.module('AnimalsListAdminService', [])
    .service('AnimalsListAdminService', ['$http', '$q', function($http, $q) {
        this.getAnimals = function(filter) {
            var def = $q.defer();

            $http.post("/webapi/admin/animals", filter)
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get animals");
                });

            return def.promise;
        }

        this.getPagesCount = function(filter) {
            var def = $q.defer();

            $http.post("/webapi/admin/animals/paginator", filter)
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

            $http.get("/webapi/admin/animals/" + animalId)
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

            $http.delete("/webapi/admin/animals/" + animalId)
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

            $http.post("/webapi/admin/animals/editor", animal)
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to update animal");
                });

            return def.promise;
        }

        this.getAnimalTypes = function() {
            var def = $q.defer();

            $http.get("/webapi/animals/animal_types")
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get animal types");
                });

            return def.promise;
        }

        this.getAnimalBreeds = function(animalTypeId) {
            var def = $q.defer();

            $http.get("/webapi/animals/animal_breeds/" + animalTypeId)
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get breeds");
                });

            return def.promise;
        }

    }]);