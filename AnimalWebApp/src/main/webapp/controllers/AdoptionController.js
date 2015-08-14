/**
 * Created by oleg on 11.08.2015.
 */
adoptionModule
    .controller('AdoptionController',
        function AdoptionController($scope, AdoptionFactory) {

            $scope.header_a_f_l = "Тварини на адопцію :";

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);

            //Pages
            $scope.filter = {};
            $scope.filter.page = 1;
            $scope.filter.limit = '15';

            var ACL = this;

            $scope.animals = {};

            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 0;

            //Amount animals for adoption
            this.amountRecords = function (filter) {

                //Show spinner loading
                $scope.contentLoading++;
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

                    //hide spinner loading
                    $scope.contentLoading--;
                });
            };

            this.amountRecords($scope.filter);

            //Animals for adoption
            this.animalsForAdoption = function (filter) {

                //Show spinner loading
                $scope.contentLoading++;
                return AdoptionFactory.
                    getListOfAdoptionAnimals(filter)
                    .then(
                    function (data) {
                        $scope.animals = data;
                        console.log($scope.animals);
                    },
                    function (data) {
                        $scope.errorMessage = "Немає записів.";

                        console.log('Error.' + data)
                    }
                ).finally(function() {

                        //hide spinner loading
                        $scope.contentLoading--;
                });
            };

            this.animalsForAdoption($scope.filter);

            $scope.pageChanged = function () {
                ACL.animalsForAdoption($scope.filter);

                //scrolling on top of page
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

            AdoptionFactory.getAnimalBreeds($scope.$parent.filter.animal.type.id)
                .then(function(data) {
                    $scope.animalBreeds = data;
                },
                function(data) {
                    console.log('Animal breeds retrieval failed.')
                }).finally(function() {
                });
        };

        $scope.doFilter = function() {
            $scope.$parent.contentLoading++;
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
                    $scope.$parent.contentLoading--;
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

//$controller('AdminAnimalsEditorSetImageController',{$scope:$scope});
/*
 $scope.$parent.animalImage = response.filePath + "?timestamp=" + new Date().getTime();
 console.log($scope.animalImage);
 */
