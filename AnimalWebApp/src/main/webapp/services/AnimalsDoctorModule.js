angular.module('AnimalsDoctorModule', ['AnimalsDoctorValues', 'AnimalMedicalHistoryValues', 'AnimalsModule'])
    .service('AnimalsDoctorService', ['$http', '$q', '$window', 'AnimalsService', 'AnimalsDoctorValues', 'AnimalMedicalHistoryValues',
        function($http, $q, $window, AnimalsService, AnimalsDoctorValues, AnimalMedicalHistoryValues) {

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

                return AnimalsService.getAnimalsForAdmin(AnimalsDoctorValues.filter)
                    .then(function(data) {
                        AnimalsDoctorValues.animals.values = data;
                    });
            }

            /**
             * filter instance used for lookup.
             * @return count of rows for pagination.
             */
            this.getPagesCount = function() {

                return AnimalsService.getAnimalsPaginatorForAdmin(AnimalsDoctorValues.filter)
                    .then(function(data) {
                        AnimalsDoctorValues.totalItems.count = data.rowsCount;
                    });
            }

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            this.getAnimal = function(animalId) {

                if (AnimalsDoctorValues.animal != undefined) {
                    if (AnimalsDoctorValues.animal.id != undefined) {
                        if (AnimalsDoctorValues.animal.id == animalId) {
                            var def = $q.defer();
                            def.resolve(AnimalsDoctorValues.animal);
                            return def.promise;
                        }
                    }
                }

                return AnimalsService.getAnimalForAdmin(animalId)
                    .then(function(data) {
                        angular.copy(data, AnimalsDoctorValues.animal);
                    });
            }

            /**
             * @return list of animal types.
             */
            this.getAnimalTypes = function() {

                if (AnimalsDoctorValues.animalTypes.values.length !== 0) {
                    var def = $q.defer();
                    def.resolve(AnimalsDoctorValues.animalTypes.values);
                    return def.promise;
                }

                return AnimalsService.getAnimalTypes()
                    .then(function(data) {
                        AnimalsDoctorValues.animalTypes.values = data;
                    });
            }

            /**
             * @return list of animal services.
             */
            this.getAnimalServices = function() {

                if (AnimalsDoctorValues.animalServices.values.length !== 0) {
                    var def = $q.defer();
                    def.resolve(AnimalsDoctorValues.animalServices.values);
                    return def.promise;
                }

                return AnimalsService.getAnimalServices()
                    .then(function(data) {
                        AnimalsDoctorValues.animalServices.values = data;
                    });
            }

            /**
             * @return list of animal breeds according to animal type.
             */
            this.getAnimalBreeds = function(animalTypeId) {

                return AnimalsService.getAnimalBreeds(animalTypeId);
            }

            /**
             * filter instance used for lookup.
             * @return list of animal medical history items.
             */
            this.getMedicalHistoryItems = function(animalId) {

                return AnimalsService.getMedicalHistoryItemsForDoctor(animalId, AnimalMedicalHistoryValues.filter)
                    .then(function(data) {
                        AnimalMedicalHistoryValues.items.values = data;
                    });
            }

            /**
             * filter instance used for lookup.
             * @return count of rows for pagination.
             */
            this.getMedicalHistoryPagesCount = function(animalId) {

                return AnimalsService.getMedicalHistoryPagesCountForDoctor(animalId)
                    .then(function(data) {
                        AnimalMedicalHistoryValues.totalItems.count = data.rowsCount;
                    });
            }

            /**
             * @param itemId id of animal medical history item used for lookup.
             * @return animal medical history item instance.
             */
            this.getMedicalHistoryItem = function(itemId) {

                if (AnimalMedicalHistoryValues.item != undefined) {
                    if (AnimalMedicalHistoryValues.item.id != undefined) {
                        if (AnimalMedicalHistoryValues.item.id == itemId) {
                            var def = $q.defer();
                            def.resolve(AnimalMedicalHistoryValues.item);
                            return def.promise;
                        }
                    }
                }

                if (AnimalMedicalHistoryValues.items != undefined) {
                    if (AnimalMedicalHistoryValues.items.values.length > 0) {
                        for (var temp in AnimalMedicalHistoryValues.items.values) {
                            if (AnimalMedicalHistoryValues.items.values[temp].id == itemId) {
                                var def = $q.defer();
                                angular.copy(AnimalMedicalHistoryValues.items.values[temp], AnimalMedicalHistoryValues.item);
                                def.resolve(AnimalMedicalHistoryValues.item);
                                return def.promise;
                            }
                        }
                    }
                }

                return AnimalsService.getMedicalHistoryItemForDoctor(itemId)
                    .then(function(data) {
                        AnimalMedicalHistoryValues.item = data;
                    });
            }

            /**
             * @param itemId id of animal medical history item used for lookup.
             * delete animal medical history item.
             */
            this.getMedicalHistoryItemDelete = function(itemId) {

                return AnimalsService.getMedicalHistoryItemDeleteForDoctor(itemId)
                    .then(function(data) {
                        for (var temp in AnimalMedicalHistoryValues.items.values) {
                            if (AnimalMedicalHistoryValues.items.values[temp].id == itemId) {
                                AnimalMedicalHistoryValues.items.values.splice(temp, 1);
                                break;
                            }
                        }
                    });
            }

            /**
             * @return list of animal medical history types.
             */
            this.getAnimalMedicalHistoryTypes = function() {

                if (AnimalMedicalHistoryValues.itemTypes.values.length !== 0) {
                    var def = $q.defer();
                    def.resolve(AnimalMedicalHistoryValues.itemTypes.values);
                    return def.promise;
                }

                return AnimalsService.getAnimalMedicalHistoryTypesForDoctor()
                    .then(function(data) {
                        AnimalMedicalHistoryValues.itemTypes.values = data;
                    });
            }

            /**
             * @param item - animal medical history item instance to be updated
             * update animal medical history item.
             */
            this.updateAnimalMedicalHistoryItem = function(item) {

                return AnimalsService.updateAnimalMedicalHistoryItemForDoctor(item);
            }

        }]);