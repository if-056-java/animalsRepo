/**
 * Created by oleg on 11.08.2015.
 */
adoptionModule
    .factory('AdoptionFactory',
        function AdoptionFactory($http, $q, RESOURCES){

        //Create instance of current factory
        var factory = {};

        //Get amount animals for adoption
        factory.getAmountRecords = function(filter) {
            var def = $q.defer();

            $http.post(RESOURCES.ANIMALS_FOR_ADOPTING_PAGINATOR, filter)
                .success(function (data) {
                    def.resolve(data);
                })
                .error(function () {
                    def.reject("Failed to get animals");
                });

            return def.promise;
        };

        //Get list of animals for adoption
        factory.getListOfAdoptionAnimals = function(filter){
            var def = $q.defer();

            $http.post(RESOURCES.ANIMALS_FOR_ADOPTING, filter)
                .success(function (data) {
                    def.resolve(data);
                })
                .error(function () {
                    def.reject("Failed to get animals");
                });

            return def.promise;
        };

            factory.getAnimalTypes = function() {
                var def = $q.defer();

                $http.get(RESOURCES.ANIMAL_TYPES)
                    .success(function(data) {
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
            }


            //Inject dependencies
        AdoptionFactory.$inject = ['$q', 'RESOURCES'];

        return factory;
        }
    );