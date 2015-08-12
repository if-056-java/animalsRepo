/**
 * Created by oleg on 11.08.2015.
 */
adoptionModule
    .controller('AdoptionController',
        function AdoptionController($scope, AdoptionFactory) {

            $scope.header_a_f_l = "Тварини на адопцію :";

            //initialize loading spinner
            var target = document.getElementById('loading-block')
            new Spinner(opts).spin(target);

            //Pages
            $scope.filter = {};
            $scope.filter.page = 1;
            $scope.filter.limit = '15';

            var ACL = this;

            $scope.animals = {};

            //This variable decides when spinner loading is closed.
            $scope.loading = 0;

            //Amount animals for adoption
            this.amountRecords = function (filter) {
                $scope.loading++;
                return AdoptionFactory.
                    getAmountRecords(filter)
                    .then(
                    function (data) {
                        $scope.totalItems = data.rowsCount;
                    },
                    function (data) {
                        $scope.totalItems = 0;

                        $scope.$parent.errorMessage = "Немає записів.";

                        console.log('Error.' + data)
                    }
                ).finally(function() {
                    // called no matter success or failure
                    $scope.loading--;
                });
            };

            this.amountRecords($scope.filter);

            //Animals for adoption
            this.animalsForAdoption = function (filter) {
                $scope.loading++;
                return AdoptionFactory.
                    getListOfAdoptionAnimals(filter)
                    .then(
                    function (data) {
                        $scope.animals = data;
                    },
                    function (data) {
                        $scope.errorMessage = "Немає записів.";

                        console.log('Error.' + data)
                    }
                ).finally(function() {
                        // called no matter success or failure
                        $scope.loading--;
                });
            };

            this.animalsForAdoption($scope.filter);

            $scope.pageChanged = function () {
                ACL.animalsForAdoption($scope.filter);
                jQuery('html, body').animate({ scrollTop: 0 }, 500);
            };

            $scope.countChanged = function (count) {
                $scope.filter.limit = count;
                ACL.animalsForAdoption($scope.filter);
            };

            //Dependency injection
            AdoptionController.$inject = ['$scope', 'AdoptionFactory'];

        }).controller('AdoptionFilterController',
            function AdoptionFilterController($scope, AdoptionFactory) {

        this.getAnimalTypes = function() {
            AdoptionFactory.getAnimalTypes()
                .then(function(data) {
                    $scope.animalTypes = data;
                },
                function(data) {
                    console.log('Animal retrieval failed.')
                });
        };

        this.getAnimalTypes();

        $scope.getAnimalBreeds = function() {
            AdoptionFactory.getAnimalBreeds($scope.filter.animal.type.id)
                .then(function(data) {
                    $scope.animalBreeds = data;
                },
                function(data) {
                    console.log('Animal breeds retrieval failed.')
                });
        };

        $scope.doFilter = function() {
            $scope.$parent.loading++;
            AdoptionFactory.getAmountRecords($scope.filter)
                .then(function(data) {
                    $scope.$parent.totalItems = data.rowsCount;
                },
                function(data) {
                    $scope.$parent.totalItems = 0;

                    $scope.$parent.errorMessage = "Немає записів.";

                    console.log('Pages count retrieval failed.')
                });

            AdoptionFactory.getListOfAdoptionAnimals($scope.filter)
                .then(function(data) {
                    $scope.$parent.animals = data;
                },
                function(data) {

                    $scope.$parent.errorMessage = "Немає записів.";

                    console.log('Animals retrieval failed.')
                }).finally(function() {
                    // called no matter success or failure
                    $scope.$parent.loading--;
                });
        };

        $scope.reset = function() {
                $scope.filter.animal.type = undefined;
                $scope.filter.animal.breed = undefined;
                $scope.filter.animal.size = undefined;
                $scope.filter.animal.sex = undefined;
                $scope.filter.animal.dateOfSterilization = undefined;
                $scope.filter.animal.image = undefined;

            $scope.doFilter();
        };

        //Dependency injection
        AdoptionFilterController.$inject = ['$scope', 'AdoptionFactory'];
    });