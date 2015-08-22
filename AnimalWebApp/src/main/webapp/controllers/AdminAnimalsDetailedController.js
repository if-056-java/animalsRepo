angular.module('AdminAnimalsDetailed', ['AdminAnimalsModule', 'AdminAnimalsValues'])
    .controller('AdminAnimalsDetailedController', ['$scope', '$routeParams', 'AdminAnimalsService', 'AdminAnimalsValues',
        function($scope, $routeParams, AdminAnimalsService, AdminAnimalsValues) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 1;

            var animalId = $routeParams.animalId;       //animal id
            $scope.animalImage = undefined;
            $scope.animal = AdminAnimalsValues.animal;  //animal

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            AdminAnimalsService.getAnimal(animalId)
                .finally(function() {
                    $scope.contentLoading--;
                });

            /*if (($scope.animal.image != undefined) && ($scope.animal.image.length > 0)) {
                $scope.animalImage = $scope.animal.image + "?timestamp=" + new Date().getTime();
            }*/

            /**
             * delete animal.
             */
            $scope.deleteAnimal = function() {
                AdminAnimalsService.deleteAnimal($scope.animal.id)
                    .then(function(data) {
                        $window.location.href = "#/ua/user/home/animals";
                    },
                    function(data) {
                        $window.alert("Animal delete failed.");
                    });
            }

        }]);