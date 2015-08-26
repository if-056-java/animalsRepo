angular.module('DoctorAnimalsModule', ['DoctorAnimalsValues', 'AnimalMedicalHistoryValues'])
    .service('DoctorAnimalsService', ['$http', '$q', 'DoctorAnimalsValues', 'AnimalMedicalHistoryValues',
        function($http, $q, DoctorAnimalsValues, AnimalMedicalHistoryValues) {

            /**
             * filter instance used for lookup.
             * @return list of animals.
             */
            this.getAnimals = function() {
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

            /**
             * filter instance used for lookup.
             * @return list of animals.
             */
            this.getMedicalHistoryItems = function(animalId) {
                var def = $q.defer();

                $http.post("/webapi/doctor/medical_history/" + animalId, AnimalMedicalHistoryValues.filter)
                    .success(function(data) {
                        AnimalMedicalHistoryValues.items.values = data;
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get medical history items");
                    });

                return def.promise;
            }

            /**
             * filter instance used for lookup.
             * @return count of rows for pagination.
             */
            this.getMedicalHistoryPagesCount = function(animalId) {
                var def = $q.defer();

                $http.get("/webapi/doctor/medical_history/paginator/" + animalId)
                    .success(function(data) {
                        AnimalMedicalHistoryValues.totalItems.count = data.rowsCount;
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get medical history pages count");
                    });

                return def.promise;
            }

            this.getMedicalHistoryItem = function(itemId) {
                var def = $q.defer();

                if (AnimalMedicalHistoryValues.item != undefined) {
                    if (AnimalMedicalHistoryValues.item.id != undefined) {
                        if (AnimalMedicalHistoryValues.item.id == itemId) {
                            def.resolve(AnimalMedicalHistoryValues.item);
                            return def.promise;
                        }
                    }
                }

                if (AnimalMedicalHistoryValues.items != undefined) {
                    if (AnimalMedicalHistoryValues.items.values.length > 0) {
                        for (var temp in AnimalMedicalHistoryValues.items.values) {
                            if (AnimalMedicalHistoryValues.items.values[temp].id == itemId) {
                                angular.copy(AnimalMedicalHistoryValues.items.values[temp], AnimalMedicalHistoryValues.item);
                                def.resolve(AnimalMedicalHistoryValues.item);
                                return def.promise;
                            }
                        }
                    }
                }

                $http.get("/webapi/doctor/medical_history/item/" + itemId)
                    .success(function(data) {
                        AnimalMedicalHistoryValues.item = data;
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get medical history item");
                    });

                return def.promise;
            }

            this.getMedicalHistoryItemDelete = function(itemId) {
                var def = $q.defer();

                $http.delete("/webapi/doctor/medical_history/item/" + itemId)
                    .success(function(data) {
                        for (var temp in AnimalMedicalHistoryValues.items.values) {
                            if (AnimalMedicalHistoryValues.items.values[temp].id == itemId) {
                                AnimalMedicalHistoryValues.items.values.splice(temp, 1);
                                break;
                            }
                        }
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to delete medical history item");
                    });

                return def.promise;
            }

            /**
             * @return list of animal medical history types.
             */
            this.getAnimalMedicalHistoryTypes = function() {
                var def = $q.defer();

                if (AnimalMedicalHistoryValues.itemTypes.values.length !== 0) {
                    def.resolve(AnimalMedicalHistoryValues.itemTypes.values);
                    return def.promise;
                }

                $http.get("/webapi/animals/medical_history/types")
                    .success(function(data) {
                        AnimalMedicalHistoryValues.itemTypes.values = data;
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get animal medical history types");
                    });

                return def.promise;
            }

            this.setAnimalMedicalHistoryItem = function(item) {
                var def = $q.defer();

                $http.post("/webapi/doctor/medical_history/item", item)
                    .success(function(data) {
                        AnimalMedicalHistoryValues.itemTypes.values = data;
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get animal medical history types");
                    });

                return def.promise;
            }

        }]);