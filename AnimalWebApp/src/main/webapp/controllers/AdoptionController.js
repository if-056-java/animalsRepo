/**
 * Created by oleg on 11.08.2015.
 */
adoptionModule
    .controller('AdoptionController',
        function AdoptionController($scope, AdoptionFactory, AnimalAdoptionValues) {

            $scope.header_a_f_l = "Тварини на адопцію :";

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);

            $scope.contentLoading = 0;

            //Pages
            $scope.filter = AnimalAdoptionValues.filter;            //filter
            $scope.totalItems = AnimalAdoptionValues.totalItems;    //table rows count
            $scope.animals = AnimalAdoptionValues.animals;          //animal instance

            console.log($scope.totalItems.count);
            /**
             * @return count of rows for pagination.
             */
            $scope.contentLoading++;
            AdoptionFactory.getAmountRecords()
                .finally(function() {
                    $scope.contentLoading--;
                });

            $scope.contentLoading++;
            AdoptionFactory.getListOfAdoptionAnimals()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return next page.
             */
            $scope.pageChanged = function() {
                AdoptionFactory.getListOfAdoptionAnimals();
                jQuery('html, body').animate({ scrollTop: 0 }, 500);
            };

            /**
             * @return list of animals with given count of rows.
             */
            $scope.countChanged = function(count) {
                $scope.filter.limit = count;
                AdoptionFactory.getListOfAdoptionAnimals();
            };

            //Dependency injection
            AdoptionController.$inject = ['$scope', 'AdoptionFactory', 'AnimalAdoptionValues'];

        }).controller('AdoptionFilterController',
            function AdoptionFilterController($scope, AdoptionFactory, AnimalAdoptionValues) {

                $scope.filter = AnimalAdoptionValues.filter;                  //filter
                $scope.animalTypes = AnimalAdoptionValues.animalTypes;        //list of animal types

                /**
                 * @return list of animal types.
                 */
                AdoptionFactory.getAnimalTypes()
                    .finally(function() {
                    });

                /**
                 * @return list of animal breeds according to animal type.
                 */
                $scope.getAnimalBreeds = function() {
                    AdoptionFactory.getAnimalBreeds($scope.filter.animal.type.id)
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

                    console.log($scope.totalItems.count);
                    $scope.doFilter();
                    jQuery('html, body').animate({ scrollTop: 0 }, 500);
                };

                /**
                 * @return list of animals according to filter values.
                 */
                $scope.doFilter = function() {
                    $scope.$parent.contentLoading++;

                    AdoptionFactory.getAmountRecords();
                    AdoptionFactory.getListOfAdoptionAnimals().finally(
                        function(){
                            $scope.$parent.contentLoading--;
                        }
                    );

                    jQuery('html, body').animate({ scrollTop: 0 }, 500);
                };

        //Dependency injection
        AdoptionFilterController.$inject = ['$scope', 'AdoptionFactory', 'AnimalAdoptionValues'];
    });