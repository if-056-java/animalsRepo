angular.module('AdminAnimalsEditor', ['nya.bootstrap.select', 'ui.bootstrap', 'AdminAnimalsModule'])
    .controller('AdminAnimalsEditorController', ['$scope', 'AdminAnimalsService', '$routeParams', '$window', '$filter',
        function($scope, AdminAnimalsService, $routeParams, $window, $filter) {
            $scope.goBack = function() {
                $window.history.back();
            }

            this.getAnimalTypes = function() {
                AdminAnimalsService.getAnimalTypes()
                    .then(function(data) {
                        $scope.animalTypes = data;
                    },
                    function(data) {
                        console.log('Animal retrieval failed.')
                    });
            }

            this.getAnimalTypes();

            var animalId = $routeParams.animalId;

            $scope.getAnimalBreeds = function() {
                $scope.animalBreeds = undefined;

                AdminAnimalsService.getAnimalBreeds($scope.animal.type.id)
                    .then(function(data) {
                        $scope.animalBreeds = data;
                    },
                    function(data) {
                        console.log('Animal breeds retrieval failed.')
                    });

                if ($scope.animal.breed != undefined) {
                    if ($scope.animal.breed.type != undefined) {
                        if ($scope.animal.breed.type.id != $scope.animal.type.id) {
                            $scope.animal.breed = undefined;
                        }
                    } else {
                        $scope.animal.breed = undefined;
                    }
                }
            }

            this.getAnimal = function(animalId) {
                AdminAnimalsService.getAnimal(animalId)
                    .then(function(data) {
                        $scope.animal = data;
                        $scope.getAnimalBreeds();
                    },
                    function(data) {
                        console.log('Animal retrieval failed.')
                    });

            };

            this.getAnimal(animalId);

            $scope.setAnimal = function() {
                $scope.animal.dateOfBirth = $filter('date')($scope.animal.dateOfBirth, 'yyyy-MM-dd');
                $scope.animal.dateOfSterilization = $filter('date')($scope.animal.dateOfSterilization, 'yyyy-MM-dd');
                $scope.animal.dateOfRegister = $filter('date')($scope.animal.dateOfRegister, 'yyyy-MM-dd');
                if (typeof $scope.animal.breed.id == "undefined") {
                    $scope.animal.breed = {breedUa: $scope.animal.breed};
                }
                AdminAnimalsService.updateAnimal($scope.animal)
                    .then(function(data) {
                        $window.location.href = "#/ua/user/home/animals/" + $scope.animal.id;
                    },
                    function(data) {
                        console.log('Animal update failed.')
                    });
            }
    }])
    .controller('DPController', ['$scope', function($scope) {
        
        $scope.clear = function () {
            $scope.dt = null;
        };

        // Disable weekend selection
        $scope.disabled = function(date, mode) {
            return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
        };

        $scope.open = function($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.opened = true;
        };
  
        $scope.dateOptions = {
            formatYear: 'yy',
            startingDay: 1
        };
  
        var tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        var afterTomorrow = new Date();
        afterTomorrow.setDate(tomorrow.getDate() + 2);
        $scope.events = [
            {date: tomorrow, status: 'full'},
            {date: afterTomorrow, status: 'partially'}
        ];

        $scope.getDayClass = function(date, mode) {
            if (mode === 'day') {
                var dayToCheck = new Date(date).setHours(0,0,0,0);

                for (var i=0;i<$scope.events.length;i++){
                    var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

                    if (dayToCheck === currentDay) {
                        return $scope.events[i].status;
                    }
                }
            }

            return '';
        };
        
    }]);