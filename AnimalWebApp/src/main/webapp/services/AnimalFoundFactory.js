/**
 * Created by oleg on 11.08.2015.
 */
animalFoundModule
    .factory('AnimalFoundFactory',
        function AnimalFoundFactory($http, $q, RESOURCES, AnimalFoundValues){

        //Create instance of current factory
        var factory = {};

        //Get amount animals for adoption
        factory.getAmountRecords = function() {
            var def = $q.defer();

            $http.post(RESOURCES.ANIMALS_FOUND_PAGINATOR, AnimalFoundValues.filter)
                .success(function (data) {
                    AnimalFoundValues.totalItems.count = data.rowsCount;
                    def.resolve(data);
                })
                .error(function (error) {
                    AnimalFoundValues.totalItems.count = 0;
                    def.reject("Failed to get animals");
                });

            return def.promise;
        };

        //Get list of animals for adoption
        factory.getListOfFoundAnimals = function(){
            var def = $q.defer();

            $http.post(RESOURCES.ANIMALS_FOUND, AnimalFoundValues.filter)
                .success(function (data) {
                    AnimalFoundValues.animals.values = data;
                    def.resolve(data);
                })
                .error(function () {
                    def.reject("Failed to get animals");
                });

            return def.promise;
        };

            factory.getAnimalTypes = function() {
                var def = $q.defer();

                if (AnimalFoundValues.animalTypes.values.length !== 0) {
                    def.resolve(AnimalFoundValues.animalTypes.values);
                    return def.promise;
                }

                $http.get(RESOURCES.ANIMAL_TYPES)
                    .success(function(data) {
                        AnimalFoundValues.animalTypes.values = data;
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
        AnimalFoundFactory.$inject = ['$q', 'RESOURCES', 'AnimalFoundValues'];

        return factory;
        }
    );