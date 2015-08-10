/**
 * Created by oleg on 09.08.2015.
 */
angular.module('animalApp')
    .factory('AdoptionFactory',
        function AdoptionFactory($resource, $q, RESOURCES){

        //Create instance of current factory
        var factory = {};

        //Get amount animals for adoption
        factory.getAmountRecords = function(limit){

            //Create instance of Defer
            var def = $q.defer();

            //Do request to REST server
            $resource(RESOURCES.ANIMALS_FOR_ADOPTING_PAGINATOR, {}, {

                //Choose method of request
                query: {
                    method: 'GET',
                    params: {},
                    isArray: false
                }
            })
                //callback current function
                .query()

                //do logic of promise object
                .$promise.then(

                //success query
                function(response){
                    //return values
                    def.resolve(response);
                },

                //error in query
                function(error){
                    //return info about error
                    def.reject(error);
                }
            );

            //return completed Defer instance
            return def.promise;
        };

        //Get list of animals for adoption
        factory.getListOfAdoptionAnimals = function(page, limits){
            var def = $q.defer();

            $resource(RESOURCES.ANIMALS_FOR_ADOPTING + ':page/:limits', {}, {
                query: {
                    method: 'GET',
                    params: {page:page, limits:limits},
                    isArray: true
                }
            })
                .query()
                .$promise.then(

                    //success query
                    function(response){
                        def.resolve(response);
                    },

                    //error in query
                    function(error){
                        def.reject(error);
                    }
                );

            return def.promise;
        };

        //Inject dependencies
        AdoptionFactory.$inject = ['ngResource', '$q', 'RESOURCES'];

        return factory;
        }
    );