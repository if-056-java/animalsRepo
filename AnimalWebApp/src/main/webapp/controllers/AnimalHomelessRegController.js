/**
 * Created by oleg on 13.08.2015.
 */
animalRegistrationModule
    .controller('AnimalHomelessRegController',
        function AnimalHomelessRegController($scope, AnimalRegistrationFactory, localStorageService,
                                             AnimalRegistrationValues, AnimalRegistrationConstants, angularPopupBoxes, $filter, $rootScope) {

            var successMessage = $filter('translate')('ANIMAL_REGISTERED');
            var failedBreedMessage = $filter('translate')('ANIMAL_BREED_NOT_FOUND');
            //alert success registration
            $scope.alertSample = function() {
                angularPopupBoxes.alert(successMessage).result.then(function() {
                    location.href="#/ua/user/profile";
                });
            };

            //alert failed breed registration
            $scope.alertSample1 = function() {
                angularPopupBoxes.alert(failedBreedMessage).result.then(function() {});
            };

            //array list of error from back - end validation
            $scope.errors = [];

            $scope.animal = {
                service: { id: AnimalRegistrationConstants.ANIMAL_REGISTRATION_HOMELESS_ID },
                user: { id: localStorageService.get('userId') },
                dateOfRegister: AnimalRegistrationValues.dateOfRegister.now,
                active: AnimalRegistrationConstants.ANIMAL_IS_ACTIVE
            };

            //This variable include info about address
            $scope.address = AnimalRegistrationValues.address;


            //Insert homeless animal
            $scope.insertHomelessAnimal = function (animal) {
                $scope.animal.address = $scope.address.country + ' ' +
                                        $scope.address.town + ' ' +
                                        $scope.address.street + ' ' +
                                        $scope.address.index;

                if (typeof $scope.animal.breed != "undefined") {
                    if (typeof $scope.animal.breed.id != "undefined") {
                        //$scope.animal.breed = {breedUa: $scope.animal.breed};
                        return AnimalRegistrationFactory
                            .insertHomelessAnimal(animal)
                            .then(
                            function(){
                                $scope.errors = [];

                                //redirect after success registration
                                $scope.alertSample();
                            },
                            function(error){
                                $scope.errors = error;
                            }
                        )
                            .finally(function() {
                                //clear fields
                                AnimalRegistrationValues.address = {
                                    country:'',
                                    town:'',
                                    street:'',
                                    index:''
                                };
                            });
                    }
                    else{
                        $scope.alertSample1();
                    }
                }
            };

            $scope.submitForm = function(isValid){
                if(isValid)
                    $scope.insertHomelessAnimal($scope.animal);
                else
                    alert('Невідома помилка.');
            };

            //Dependency injection
            AnimalHomelessRegController.$inject = ['$scope', 'AnimalRegistrationFactory', 'localStorageService',
                                                   'AnimalRegistrationValues', 'AnimalRegistrationConstants', 'angularPopupBoxes', '$filter'];
        }
    );