/**
 * Created by oleg on 13.08.2015.
 */
animalRegistrationModule
    .factory('AnimalRegistrationFactory',
        function AnimalRegistrationFactory ($http, $q, RESOURCES) {

            //Create instance of current factory
            var factory = {};

            //Insert new homeless animal
            factory.insertHomelessAnimal = function(animal){
                var def = $q.defer();

                $http.post(RESOURCES.ANIMAL_REGISTRATION, animal)
                    .success(function (data) {
                        def.resolve(data);
                    })
                    .error(function (error) {
                        console.log(error);
                        def.reject("Failed to insert animals");
                    });

                return def.promise;
            };

            //Inject dependencies
            AnimalRegistrationFactory.$inject = ['$q', 'RESOURCES'];

            return factory;
        }
    );

animalRegistrationModule
    .factory('AnimalDetailFactory',
        function AnimalDetailFactory ($http, $q, RESOURCES) {

            //Create instance of current factory
            var factory = {};

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
            };

            //Inject dependencies
            AnimalDetailFactory.$inject = ['$q', 'RESOURCES'];

            return factory;
        }
    );