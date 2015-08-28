angular.module('AnimalsAdminController', ['AnimalsAdminModule', 'nya.bootstrap.select', 'DPController', 'AnimalsAdminValues', 'LocalStorageModule'])
    .controller('AnimalsAdminController', ['$scope', '$http', 'AnimalsAdminService', 'AnimalsAdminValues', 'localStorageService',
        function($scope, $http, AnimalsAdminService, AnimalsAdminValues, localStorageService) {

            // Sending message to Twitter (remove from here into Service?????????)
            $scope.sendTwitter = function (id) {
                console.log("twitt sended");
                console.log(id);
        //           $http.defaults.headers.common['AccessToken'] = localStorageService.get("accessToken");
       //         console.log($http.defaults.headers.common['AccessToken']);

                $http.post("/webapi/socials/twitter/" + id)
                    .success(function (data) {
                        console.log("twitt sended");
                        console.log(data);
                    });
            };

            // Sending message to Facebook (remove from here into Service?????????)
            $scope.sendFacebook = function (id) {
            //    $http.defaults.headers.common['AccessToken'] = localStorageService.get("accessToken");
                $http.post("/webapi/socials/facebook/" + id)
                    .success(function (data) {
                        console.log("facebook post sended");
                        console.log(data);
                    });
            };

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 4;

            $scope.filter = AnimalsAdminValues.filter;            //filter
            $scope.totalItems = AnimalsAdminValues.totalItems;    //table rows count
            $scope.animals = AnimalsAdminValues.animals;          //animal instance

            /**
             * @return count of rows for pagination.
             */
            AnimalsAdminService.getPagesCount()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return list of animals.
             */
            AnimalsAdminService.getAnimals()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return next page.
             */
            $scope.pageChanged = function() {
                AnimalsAdminService.getAnimals();
            };

            /**
             * @return list of animals with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                AnimalsAdminService.getAnimals();
            };
    }])
    .controller('AnimalsFilterAdminController', ['$scope', '$filter', 'AnimalsAdminService', 'AnimalsAdminValues',
        function($scope, $filter, AnimalsAdminService, AnimalsAdminValues) {

            $scope.filter = AnimalsAdminValues.filter;                  //filter
            $scope.animalTypes = AnimalsAdminValues.animalTypes;        //list of animal types
            $scope.animalServices = AnimalsAdminValues.animalServices;  //list of animal services

            /**
             * @return list of animal types.
             */
            AnimalsAdminService.getAnimalTypes()
                .finally(function() {
                    $scope.$parent.contentLoading--;
                });

            /**
             * @return list of animal types.
             */
            AnimalsAdminService.getAnimalServices()
                .finally(function() {
                    $scope.$parent.contentLoading--;
                });

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.getAnimalBreeds = function() {
                $scope.filterAnimalBreedFlag = true;
                AnimalsAdminService.getAnimalBreeds($scope.filter.animal.type.id)
                    .then(function(data) {
                        $scope.animalBreeds = data;
                    })
                    .finally(function() {
                        $scope.filterAnimalBreedFlag = false;
                    });
            }

            /**
             * reset filter values.
             */
            $scope.reset = function() {
                $scope.filter.animal.transpNumber = undefined;
                $scope.filter.animal.service = undefined;
                $scope.filter.animal.type = undefined;
                $scope.filter.animal.breed = undefined;
                $scope.filter.animal.sex = undefined;
                $scope.filter.animal.dateOfRegister = undefined;
            }

            /**
             * @return list of animals according to filter values.
             */
            $scope.doFilter = function() {
                $scope.filter.animal.dateOfRegister = $filter('date')($scope.filter.animal.dateOfRegister, 'yyyy-MM-dd');

                AnimalsAdminService.getPagesCount();
                AnimalsAdminService.getAnimals();
            };
    }]);