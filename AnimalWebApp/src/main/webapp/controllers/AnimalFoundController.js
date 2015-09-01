/**
 * Created by oleg on 11.08.2015.
 */
animalFoundModule
    .controller('AnimalFoundController',
        function AnimalFoundController($scope, AnimalFoundFactory, AnimalFoundValues) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);

            $scope.contentLoading = 0;
            $scope.errorMessage = '';

            //Pages
            $scope.filter = AnimalFoundValues.filter;            //filter
            $scope.totalItems = AnimalFoundValues.totalItems;    //table rows count
            $scope.animals = AnimalFoundValues.animals;          //animal instance

            /**
             * @return count of rows for pagination.
             */
            $scope.contentLoading++;
            AnimalFoundFactory.getAmountRecords()
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
            AnimalFoundFactory.getListOfFoundAnimals()
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

                AnimalFoundFactory.getListOfFoundAnimals().finally(function() {
                    $scope.contentLoading--;
                });
            };

            /**
             * @return list of animals with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                AnimalFoundFactory.getListOfFoundAnimals();
            };

            //Dependency injection
            AnimalFoundController.$inject = ['$scope', 'AnimalFoundFactory', 'AnimalFoundValues'];

        }).controller('AnimalFoundFilterController',
            function AnimalFoundFilterController($scope, AnimalFoundFactory, AnimalFoundValues, $window) {

                //locale
                $scope.currentLanguage = $window.localStorage.getItem('NG_TRANSLATE_LANG_KEY');

                $scope.filter = AnimalFoundValues.filter;                  //filter
                $scope.animalTypes = AnimalFoundValues.animalTypes;        //list of animal types

                /**
                 * @return list of animal types.
                 */
                AnimalFoundFactory.getAnimalTypes()
                    .finally(function() {
                    });

                /**
                 * @return list of animal breeds according to animal type.
                 */
                $scope.getAnimalBreeds = function() {
                    AnimalFoundFactory.getAnimalBreeds($scope.filter.animal.type.id)
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

                    AnimalFoundFactory.getAmountRecords()
                        .then(
                        function(result){},

                        //fail
                        function(error){
                             $scope.totalItems.count = 0;
                             $scope.$parent.errorMessage = error.toString();
                        });

                    AnimalFoundFactory.getListOfFoundAnimals().finally(
                        function(){
                            $scope.$parent.contentLoading--;
                        }
                    );
                    console.log($scope.totalItems.count);

                    jQuery('html, body').animate({ scrollTop: 0 }, 500);
                };

        //Dependency injection
        AnimalFoundFilterController.$inject = ['$scope', 'AnimalFoundFactory', 'AnimalFoundValues', '$window'];
    });