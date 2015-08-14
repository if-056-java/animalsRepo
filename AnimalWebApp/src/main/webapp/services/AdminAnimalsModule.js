angular.module('AdminAnimalsModule', [])
    .service('AdminAnimalsService', ['$http', '$q', function($http, $q) {

        /**
         * @param filter instance used for lookup.
         * @return list of animals.
         */
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

        /**
         * @param filter instance used for lookup.
         * @return count of rows for pagination.
         */
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

        /**
         * @param animalId id of animal used for lookup.
         * @return animal instance.
         */
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

        /**
         * delete animal.
         */
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

        /**
         * update animal.
         */
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

        /**
         * @return list of animal types.
         */
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

        /**
         * @return list of animal breeds according to animal type.
         */
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