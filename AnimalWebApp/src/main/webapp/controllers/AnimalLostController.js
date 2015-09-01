/**
 * Created by oleg on 11.08.2015.
 */
animalLostModule
    .controller('AnimalLostController',
        function AnimalLostController($scope, AnimalLostFactory, AnimalLostValues) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);

            $scope.contentLoading = 0;
            $scope.errorMessage = '';

            //Pages
            $scope.filter = AnimalLostValues.filter;            //filter
            $scope.totalItems = AnimalLostValues.totalItems;    //table rows count
            $scope.animals = AnimalLostValues.animals;          //animal instance

            /**
             * @return count of rows for pagination.
             */
            $scope.contentLoading++;
            AnimalLostFactory.getAmountRecords()
                .then(
                    function(result){},

                    //fail
                    function(error){
                        $scope.totalItems.count = 0;
                        $scope.errorMessage = error;
                    }
                )
                .finally(function() {
                    $scope.contentLoading--;
                });

            $scope.contentLoading++;
            AnimalLostFactory.getListOfLostAnimals()
                .then(
                    function(result){},

                    //fail
                    function(error){
                        $scope.errorMessage = error;
                    }
                )
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return next page.
             */
            $scope.pageChanged = function() {
                $scope.contentLoading++;

                //scroll to top of the page
                jQuery('html, body').animate({ scrollTop: 0 }, 500);

                AnimalLostFactory.getListOfLostAnimals().finally(function() {
                    $scope.contentLoading--;
                });
            };

            /**
             * @return list of animals with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                AnimalLostFactory.getListOfLostAnimals();
            };

            //Dependency injection
            AnimalLostController.$inject = ['$scope', 'AnimalLostFactory', 'AnimalLostValues'];

        }).controller('AnimalLostFilterController',
            function AnimalLostFilterController($scope, AnimalLostFactory, AnimalLostValues) {

                $scope.filter = AnimalLostValues.filter;                  //filter
                $scope.animalTypes = AnimalLostValues.animalTypes;        //list of animal types

                /**
                 * @return list of animal types.
                 */
                AnimalLostFactory.getAnimalTypes()
                    .finally(function() {
                    });

                /**
                 * @return list of animal breeds according to animal type.
                 */
                $scope.getAnimalBreeds = function() {
                    AnimalLostFactory.getAnimalBreeds($scope.filter.animal.type.id)
                        .then(function(data) {
                            $scope.animalBreeds = data;
                        })
                        .finally(function() {
                        });
                };

                /**
                 * reset filter values.
                 */
                $scope.reset = function() {
                    $scope.filter.animal.type = undefined;
                    $scope.filter.animal.breed = undefined;
                    $scope.filter.animal.sex = undefined;
                    $scope.filter.animal.dateOfRegister = undefined;

                    $scope.doFilter();
                    jQuery('html, body').animate({ scrollTop: 0 }, 500);
                };

                /**
                 * @return list of animals according to filter values.
                 */
                $scope.doFilter = function() {
                    $scope.$parent.contentLoading++;

                    AnimalLostFactory.getAmountRecords()
                        .then(
                        function(result){},

                        //fail
                        function(error){
                             $scope.totalItems.count = 0;
                             $scope.$parent.errorMessage = error.toString();
                        });

                    AnimalLostFactory.getListOfLostAnimals().finally(
                        function(){
                            $scope.$parent.contentLoading--;
                        }
                    );
                    console.log($scope.totalItems.count);

                    jQuery('html, body').animate({ scrollTop: 0 }, 500);
                };

        //Dependency injection
        AnimalLostFilterController.$inject = ['$scope', 'AnimalLostFactory', 'AnimalLostValues'];
    });