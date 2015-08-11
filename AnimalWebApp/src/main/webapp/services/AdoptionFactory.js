/**
 * Created by oleg on 11.08.2015.
 */
adoptionModule
    .factory('AdoptionFactory',
        function AdoptionFactory($http, $q, RESOURCES){

        //Create instance of current factory
        var factory = {};

        //Get amount animals for adoption
        factory.getAmountRecords = function() {
            var def = $q.defer();

            $http({
                url: RESOURCES.ANIMALS_FOR_ADOPTING_PAGINATOR,
                method: 'GET',
                isArray: false
            })
                .success(function (data) {
                    def.resolve(data);
                })
                .error(function () {
                    def.reject("Failed to get animals");
                });

            return def.promise;
        };

        //Get list of animals for adoption
        factory.getListOfAdoptionAnimals = function(page, limits){
            var def = $q.defer();

            $http({
                url: RESOURCES.ANIMALS_FOR_ADOPTING + page + '/' + limits,
                method: 'GET',
                isArray: true
            })
                .success(function(data) {
                    def.resolve(data);
                })
                .error(function() {
                    def.reject("Failed to get animals");
                });

            return def.promise;
        };

        //Inject dependencies
        AdoptionFactory.$inject = ['$q', 'RESOURCES'];

        return factory;
        }
    );