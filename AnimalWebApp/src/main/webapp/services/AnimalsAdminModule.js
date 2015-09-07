angular.module('AnimalsAdminModule', ['AnimalsAdminValues', 'AnimalsModule'])
    .service('AnimalsAdminService', ['$http', '$q', '$window', 'AnimalsAdminValues', 'AnimalsService',
        function($http, $q, $window, AnimalsAdminValues, AnimalsService) {

            this.rolesAllowed = function(role) {
                if ( !AnimalsService.rolesAllowed(role) ) {
                    $window.location.href = "#ua";
                }
            }

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

                return AnimalsService.updateAnimalForAdmin(animal);
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

            /**
             * @param id
             * sending message to Twitter.
             */
            this.sendTwitter = function (id) {

                console.log("twitt sended");
                console.log(id);

                return AnimalsService.sendTwitter(id)
                    .then(function(data) {
                        console.log(data);
                    });
            };

            /**
             * @param id
             * sending message to Facebook.
             */
            this.sendFacebook = function (id) {

                return AnimalsService.sendFacebook(id)
                    .then(function(data) {
                        console.log("facebook post sended");
                        console.log(data);
                    });
            };

            /**
             * @param animalId id of animal used for lookup.
             * delete animal image.
             */
            this.deleteAnimalImage = function(animalId) {

                return AnimalsService.deleteAnimalImageForAdmin(animalId)
                    .then(function(data) {
                        AnimalsAdminValues.animal.image = undefined;
                    });
            }
    }]);