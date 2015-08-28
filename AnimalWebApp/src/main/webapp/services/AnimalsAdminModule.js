angular.module('AnimalsAdminModule', ['AnimalsAdminValues', 'AnimalsModule'])
    .service('AnimalsAdminService', ['$http', '$q', 'AnimalsAdminValues', 'AnimalsService',
        function($http, $q, AnimalsAdminValues, AnimalsService) {

            /**
             * filter instance used for lookup.
             * @return list of animals.
             */
            this.getAnimals = function() {

                return AnimalsService.getAnimalsForAdmin(AnimalsAdminValues.filter)
                    .then(function(data) {
                        AnimalsAdminValues.animals.values = data;
                    });
            }

            /**
             * filter instance used for lookup.
             * @return count of rows for pagination.
             */
            this.getPagesCount = function() {

                return AnimalsService.getAnimalsPaginatorForAdmin(AnimalsAdminValues.filter)
                    .then(function(data) {
                        AnimalsAdminValues.totalItems.count = data.rowsCount;
                    });
            }

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            this.getAnimal = function(animalId) {

                if (AnimalsAdminValues.animal != undefined) {
                    if (AnimalsAdminValues.animal.id != undefined) {
                        if (AnimalsAdminValues.animal.id == animalId) {
                            var def = $q.defer();
                            def.resolve(AnimalsAdminValues.animal);
                            return def.promise;
                        }
                    }
                }

                return AnimalsService.getAnimalForAdmin(animalId)
                    .then(function(data) {
                        angular.copy(data, AnimalsAdminValues.animal);
                    });
            }

            /**
             * @param animalId id of animal used for lookup.
             * delete animal.
             */
            this.deleteAnimal = function(animalId) {

                return AnimalsService.deleteAnimalForAdmin(animalId);
            }

            /**
             * @param animal instance to be updated
             * update animal.
             */
            this.updateAnimal = function(animal) {

                return AnimalsService.updateAnimalForAdmin(animal)
                    .then(function(data) {
                        AnimalsAdminValues.animal.image = data.filePath;
                    });
            }

            /**
             * @return list of animal types.
             */
            this.getAnimalTypes = function() {

                if (AnimalsAdminValues.animalTypes.values.length !== 0) {
                    var def = $q.defer();
                    def.resolve(AnimalsAdminValues.animalTypes.values);
                    return def.promise;
                }

                return AnimalsService.getAnimalTypes()
                    .then(function(data) {
                        AnimalsAdminValues.animalTypes.values = data;
                    });
            }

            /**
             * @return list of animal services.
             */
            this.getAnimalServices = function() {

                if (AnimalsAdminValues.animalServices.values.length !== 0) {
                    var def = $q.defer();
                    def.resolve(AnimalsAdminValues.animalServices.values);
                    return def.promise;
                }

                return AnimalsService.getAnimalServices()
                    .then(function(data) {
                        AnimalsAdminValues.animalServices.values = data;
                    });
            }

            /**
             * @return list of animal breeds according to animal type.
             */
            this.getAnimalBreeds = function(animalTypeId) {

                return AnimalsService.getAnimalBreeds(animalTypeId);
            }
    }]);