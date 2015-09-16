/**
 * Created by oleg on 11.08.2015.
 */
adoptionModule
    .factory('AdoptionFactory',
        function AdoptionFactory($http, $q, RESOURCES, AnimalAdoptionValues){

        //Create instance of current factory
        var factory = {};

        //Get amount animals for adoption
        factory.getAmountRecords = function() {
            var def = $q.defer();

            $http.post(RESOURCES.ANIMALS_FOR_ADOPTING_PAGINATOR, AnimalAdoptionValues.filter)
                .success(function (data) {
                    AnimalAdoptionValues.totalItems.count = data.rowsCount;
                    def.resolve(data);
                })
                .error(function (error) {
                    AnimalAdoptionValues.totalItems.count = 0;
                    def.reject("Failed to get animals");
                });

            return def.promise;
        };

        //Get list of animals for adoption
        factory.getListOfAdoptionAnimals = function(){
            var def = $q.defer();

            $http.post(RESOURCES.ANIMALS_FOR_ADOPTING, AnimalAdoptionValues.filter)
                .success(function (data) {
                    AnimalAdoptionValues.animals.values = data;
                    def.resolve(data);
                })
                .error(function () {
                    def.reject("Failed to get animals");
                });

            return def.promise;
        };

        factory.getListOfAnimalStatuses = function(animalId){
            var def = $q.defer();

            $http.get(RESOURCES.ANIMALS_FOR_ADOPTING_STATUSES + animalId)
                .success(function (data) {
                    AnimalAdoptionValues.animalStatuses = data;
                    def.resolve(data);
                })
                .error(function () {
                    def.reject("Failed to get animal statuses");
                });

            return def.promise;
        };

            factory.getAnimalTypes = function() {
                var def = $q.defer();

                if (AnimalAdoptionValues.animalTypes.values.length !== 0) {
                    def.resolve(AnimalAdoptionValues.animalTypes.values);
                    return def.promise;
                }

                $http.get(RESOURCES.ANIMAL_TYPES)
                    .success(function(data) {
                        AnimalAdoptionValues.animalTypes.values = data;
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get animal types");
                    });

                return def.promise;
            };

            factory.getAnimalBreeds = function(animalTypeId) {
                var def = $q.defer();

                $http.get(RESOURCES.ANIMAL_BREEDS + animalTypeId)
                    .success(function(data) {
                        def.resolve(data);
                    })
                    .error(function() {
                        def.reject("Failed to get breeds");
                    });

                return def.promise;
            };

        //Inject dependencies
        AdoptionFactory.$inject = ['$q', 'RESOURCES', 'AnimalAdoptionValues'];

        return factory;
        }
    );