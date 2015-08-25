angular.module('DoctorAnimalsModule', ['DoctorAnimalsValues'])
    .service('DoctorAnimalsService', ['$http', '$q', 'DoctorAnimalsValues', function($http, $q, DoctorAnimalsValues) {

        /**
         * filter instance used for lookup.
         * @return list of animals.
         */
        this.getAnimals = function(filter) {
            var def = $q.defer();

            $http.post("/webapi/admin/animals", DoctorAnimalsValues.filter)
                .success(function(data) {
                    DoctorAnimalsValues.animals.values = data;
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get animals");
                });

            return def.promise;
        }

        /**
         * filter instance used for lookup.
         * @return count of rows for pagination.
         */
        this.getPagesCount = function() {
            var def = $q.defer();

            $http.post("/webapi/admin/animals/paginator", DoctorAnimalsValues.filter)
                .success(function(data) {
                    DoctorAnimalsValues.totalItems.count = data.rowsCount;
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

            if (DoctorAnimalsValues.animal != undefined) {
                if (DoctorAnimalsValues.animal.id != undefined) {
                    if (DoctorAnimalsValues.animal.id == animalId) {
                        def.resolve(DoctorAnimalsValues.animal);
                        return def.promise;
                    }
                }
            }

            $http.get("/webapi/admin/animals/" + animalId)
                .success(function(data) {
                    angular.copy(data, DoctorAnimalsValues.animal);
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get animal");
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
                    DoctorAnimalsValues.animal.image = data.filePath;
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

            if (DoctorAnimalsValues.animalTypes.values.length !== 0) {
                def.resolve(DoctorAnimalsValues.animalTypes.values);
                return def.promise;
            }

            $http.get("/webapi/animals/animal_types")
                .success(function(data) {
                    DoctorAnimalsValues.animalTypes.values = data;
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get animal types");
                });

            return def.promise;
        }

        /**
         * @return list of animal services.
         */
        this.getAnimalServices = function() {
            var def = $q.defer();

            if (DoctorAnimalsValues.animalServices.values.length !== 0) {
                def.resolve(DoctorAnimalsValues.animalServices.values);
                return def.promise;
            }

            $http.get("/webapi/animals/animal_services")
                .success(function(data) {
                    DoctorAnimalsValues.animalServices.values = data;
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get animal services");
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