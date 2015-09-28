/**
 * Created by oleg on 11.08.2015.
 */
adoptionModule
    .controller('AnimalAdoptionController',
        function AnimalAdoptionController($scope, $filter, AdoptionFactory, AnimalAdoptionValues) {
            //Pages
            $scope.filter = AnimalAdoptionValues.filter;            //filter

            //message with errors
            $scope.errorMessage = '';
            $scope.totalItems = AnimalAdoptionValues.totalItems;    //table rows count
            $scope.animals = AnimalAdoptionValues.animals;          //animal instance

            /**
             * Get animal list and statuses
             */
            var initList = function(){
                AdoptionFactory.getListOfAdoptionAnimals()
                    .then(
                    function(result){},
                    //fail
                    function(error){
                        $scope.errorMessage = error;
                    }
                );
            };

            /**
             * @return count of rows for pagination.
             */
            AdoptionFactory.getAmountRecords()
                .then(
                    function(){},

                    //fail
                    function(error){
                        $scope.totalItems.count = 0;
                        $scope.errorMessage = error;
                    }
                );
            initList();

            /**
             * @return next page.
             */
            $scope.pageChanged = function() {
                initList();

                //scroll to top of the page
                jQuery('html, body').animate({ scrollTop: 0 }, 500);
            };

            /**
             * @return list of animals with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                initList();
            };

            //Dependency injection
            AnimalAdoptionController.$inject = ['$scope', '$filter', 'AdoptionFactory', 'AnimalAdoptionValues', '$translate'];

        }).controller('AdoptionFilterController',
            function AdoptionFilterController($scope, $filter, AdoptionFactory, AnimalAdoptionValues, $window) {

                //locale
                $scope.currentLanguage = $window.localStorage.getItem('NG_TRANSLATE_LANG_KEY');

                $scope.filter = AnimalAdoptionValues.filter;                  //filter
                $scope.animalTypes = AnimalAdoptionValues.animalTypes;        //list of animal types

                /**
                 * Get animal list and statuses
                 */
                var initList = function(){
                    AdoptionFactory.getListOfAdoptionAnimals()
                        .then(
                        function(result){},
                        //fail
                        function(error){
                            $scope.errorMessage = error;
                        }
                    );
                };

                /**
                 * @return list of animal types.
                 */
                AdoptionFactory.getAnimalTypes()
                    .then(
                        function(){},
                        function(error){
                            $scope.errorMessage = error;
                        }
                    )
                    .finally(function() {
                    });

                /**
                 * @return list of animal breeds according to animal type.
                 */
                $scope.getAnimalBreeds = function() {
                    AdoptionFactory.getAnimalBreeds($scope.filter.animal.type.id)
                        .then(
                            function(data) {
                                $scope.animalBreeds = data;
                            },
                            function(error){
                                $scope.errorMessage = error;
                            }
                        )
                        .finally(function() {
                        });
                };

                /**
                 * reset filter values.
                 */
                $scope.reset = function() {
                    $scope.filter.animal.type = undefined;
                    $scope.filter.animal.size = undefined;
                    $scope.filter.animal.breed = undefined;
                    $scope.filter.animal.sex = undefined;
                    $scope.filter.animal.dateOfRegister = undefined;
                    $scope.filter.animal.dateOfSterilization = undefined;
                    $scope.filter.animal.image = undefined;

                    clearErrorMessage();
                    $scope.doFilter();
                    jQuery('html, body').animate({ scrollTop: 0 }, 500);
                };

                /**
                 * @return list of animals according to filter values.
                 */
                $scope.doFilter = function() {
                    AdoptionFactory.getAmountRecords()
                        .then(
                        function(){
                            if($scope.totalItems.count !== 0){
                                clearErrorMessage();
                            }
                        },
                        //fail
                        function(error){
                            $scope.totalItems.count = 0;
                            $scope.errorMessage = error;
                        });

                    initList();

                    jQuery('html, body').animate({ scrollTop: 0 }, 500);
                };

                /**
                 * Clear error messages
                 */
                function clearErrorMessage(){
                    $scope.errorMessage = '';
                }

                //Dependency injection
        AdoptionFilterController.$inject = ['$scope', '$filter', 'AdoptionFactory', 'AnimalAdoptionValues', '$window'];
    });