angular.module('animalApp')
    .controller('AdoptionController',
        function AdoptionController($scope, AdoptionFactory){

            $scope.header_a_f_l="Тварини на адопцію :";

            //Pages
            $scope.totalItems = 0;
            $scope.currentPage = 1;
            $scope.limit = '10';

            var ACL = this;

            //Amount animals for adoption
            this.amountRecords = function(limit) {
                return AdoptionFactory.
                    getAmountRecords(limit)
                    .then(
                    function (data) {
                        $scope.totalItems = data.rowsCount;
                    },
                    function (data) {
                        console.log('Error.')
                    });
            };

            this.amountRecords($scope.limit);

            //Animals for adoption
            this.animalsForAdoption = function(page, limit) {
                return AdoptionFactory.
                    getListOfAdoptionAnimals(page, limit)
                    .then(
                    function (data) {
                        $scope.animalsForAdoption = data;
                        console.log(data);
                    },
                    function (data) {
                        console.log('Error.')
                    });
            };

            this.animalsForAdoption($scope.currentPage, $scope.limit);

            $scope.pageChanged = function() {
                ACL.animalsForAdoption($scope.currentPage, $scope.limit);
            };

            $scope.countChanged = function(count) {
                $scope.limit = count;
                ACL.animalsForAdoption($scope.currentPage, $scope.limit);
            };


            //Dependency injection
            AdoptionController.$inject = ['$scope', 'ui.bootstrap', 'AdoptionFactory'];
        }
    );