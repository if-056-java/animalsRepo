/**
 * Created by oleg on 11.08.2015.
 */
adoptionModule
    .controller('AdoptionController',
        function AdoptionController($scope, AdoptionFactory){

            $scope.header_a_f_l="Тварини на адопцію :";


            //test
            $scope.imageExist = 'yes';
            //test

            //Pages
            $scope.totalItems = 0;
            $scope.currentPage = 1;
            $scope.limit = '10';

            var ACL = this;

            $scope.params = {
                imageExist: $scope.imageExist
            };

            //Amount animals for adoption
            this.amountRecords = function() {
                return AdoptionFactory.
                    getAmountRecords()
                    .then(
                    function (data) {
                        $scope.totalItems = data.rowsCount;
                    },
                    function (data) {
                        console.log('Error.' + data)
                    });
            };

            this.amountRecords();

            //Animals for adoption
            this.animalsForAdoption = function(page, limit) {
                return AdoptionFactory.
                    getListOfAdoptionAnimals(page, limit)
                    .then(
                    function (data) {
                        $scope.animalsForAdoption = data;
                    },
                    function (data) {
                        console.log('Error.' + data)
                    });
            };

            this.animalsForAdoption($scope.currentPage, $scope.limit);

            $scope.pageChanged = function() {
                ACL.animalsForAdoption($scope.currentPage, $scope.limit, $scope.params);
            };

            $scope.countChanged = function(count) {
                $scope.limit = count;
                ACL.animalsForAdoption($scope.currentPage, $scope.limit, $scope.params);
            };


            //Dependency injection
            AdoptionController.$inject = ['$scope', 'AdoptionFactory'];
        }
    );