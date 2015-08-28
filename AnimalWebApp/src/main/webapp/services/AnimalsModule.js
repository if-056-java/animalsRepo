angular.module('AnimalsModule', ['LocalStorageModule'])
    .service('AnimalsService', ['$http', '$q', 'localStorageService', 'RESOURCES',
        function($http, $q, localStorageService, RESOURCES) {

            $http.defaults.headers.common['AccessToken'] = localStorageService.get('accessToken');

            this.rolesAllowed = function(role) {
                return role == localStorageService.get('userRole');
            }

            /**
             * filter instance used for lookup.
             * @return list of animals.
             */
            this.getAnimalsForAdmin = function(filter) {
                var def = $q.defer();

                $http.post(RESOURCES.ANIMALS_FOR_ADMIN, filter)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get animals.");
                    });

                return def.promise;
            }

            /**
             * filter instance used for lookup.
             * @return count of rows for pagination.
             */
            this.getAnimalsPaginatorForAdmin = function(filter) {
                var def = $q.defer();

                $http.post(RESOURCES.ANIMALS_FOR_ADMIN_PAGINATOR, filter)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get page count.");
                    });

                return def.promise;
            }

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            this.getAnimalForAdmin = function(animalId) {
                var def = $q.defer();

                $http.get(RESOURCES.ANIMAL_FOR_ADMIN + animalId)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get animal.");
                    });

                return def.promise;
            }

            /**
             * @param animalId id of animal used for lookup.
             * delete animal.
             */
            this.deleteAnimalForAdmin = function(animalId) {
                var def = $q.defer();

                $http.delete(RESOURCES.ANIMAL_FOR_ADMIN_DELETE + animalId)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to delete animal.");
                    });

                return def.promise;
            }

            /**
             * @param animal instance to be updated
             * update animal.
             */
            this.updateAnimalForAdmin = function(animal) {
                var def = $q.defer();

                $http.post(RESOURCES.ANIMAL_FOR_ADMIN_UPDATE, animal)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to update animal.");
                    });

                return def.promise;
            }

            /**
             * @return list of animal types.
             */
            this.getAnimalTypes = function() {
                var def = $q.defer();

                $http.get(RESOURCES.ANIMAL_TYPES)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get animal types.");
                    });

                return def.promise;
            }

            /**
             * @return list of animal services.
             */
            this.getAnimalServices = function() {
                var def = $q.defer();

                $http.get(RESOURCES.ANIMAL_SERVICES)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get animal services.");
                    });

                return def.promise;
            }

            /**
             * @return list of animal breeds according to animal type.
             */
            this.getAnimalBreeds = function(animalTypeId) {
                var def = $q.defer();

                $http.get(RESOURCES.ANIMAL_BREEDS + animalTypeId)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get breeds.");
                    });

                return def.promise;
            }

            /**
             * filter instance used for lookup.
             * @return list of animal medical history items.
             */
            this.getMedicalHistoryItemsForDoctor = function(animalId, filter) {
                var def = $q.defer();

                $http.post(RESOURCES.MEDICAL_HISTORY_ITEMS_FOR_DOCTOR + animalId, filter)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get medical history items.");
                    });

                return def.promise;
            }

            /**
             * filter instance used for lookup.
             * @return count of rows for pagination.
             */
            this.getMedicalHistoryPagesCountForDoctor = function(animalId) {
                var def = $q.defer();

                $http.get(RESOURCES.MEDICAL_HISTORY_ITEMS_PAGINATOR_FOR_DOCTOR + animalId)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get medical history pages count.");
                    });

                return def.promise;
            }

            /**
             * @param itemId id of animal medical history item used for lookup.
             * @return animal medical history item instance.
             */
            this.getMedicalHistoryItemForDoctor = function(itemId) {
                var def = $q.defer();

                $http.get(RESOURCES.MEDICAL_HISTORY_ITEM_FOR_DOCTOR + itemId)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get medical history item.");
                    });

                return def.promise;
            }

            /**
             * @param itemId id of animal medical history item used for lookup.
             * delete animal medical history item.
             */
            this.getMedicalHistoryItemDeleteForDoctor = function(itemId) {
                var def = $q.defer();

                $http.delete(RESOURCES.MEDICAL_HISTORY_ITEM_FOR_DOCTOR_DELETE + itemId)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to delete medical history item.");
                    });

                return def.promise;
            }

            /**
             * @return list of animal medical history types.
             */
            this.getAnimalMedicalHistoryTypesForDoctor = function() {
                var def = $q.defer();

                $http.get(RESOURCES.MEDICAL_HISTORY_TYPES)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get animal medical history types.");
                    });

                return def.promise;
            }

            /**
             * @param item - animal medical history item instance to be updated
             * update animal medical history item.
             */
            this.updateAnimalMedicalHistoryItemForDoctor = function(item) {
                var def = $q.defer();

                $http.post(RESOURCES.MEDICAL_HISTORY_ITEM_FOR_DOCTOR_UPDATE, item)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to update animal medical history item.");
                    });

                return def.promise;
            }

            /**
             * @param id
             * sending message to Twitter.
             */
            this.sendTwitter = function (id) {
                var def = $q.defer();

                $http.post("/webapi/socials/twitter/" + id)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed sending message to twitter.");
                    });

                return def.promise;
            };

            /**
             * @param id
             * sending message to Facebook.
             */
            this.sendFacebook = function (id) {
                var def = $q.defer();

                $http.post("/webapi/socials/facebook/" + id)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed sending message to facebook.");
                    });

                return def.promise;
            };

        }]);