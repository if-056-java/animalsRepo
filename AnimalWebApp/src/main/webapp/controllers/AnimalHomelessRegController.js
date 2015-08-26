/**
 * Created by oleg on 13.08.2015.
 */
animalRegistrationModule
    .controller('AnimalHomelessRegController',
        function AnimalHomelessRegController($scope, AnimalRegistrationFactory, localStorageService,
                                             AnimalRegistrationValues, AnimalRegistrationConstants) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);

            //This variable decides when spinner loading for contents closed.
            $scope.contentLoading = 0;

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
                //Show spinner loading
                $scope.contentLoading++;

                $scope.animal.address = $scope.address.country + ' ' +
                                        $scope.address.town + ' ' +
                                        $scope.address.street + ' ' +
                                        $scope.address.index;

                if (typeof $scope.animal.breed != "undefined") {
                    if (typeof $scope.animal.breed.id == "undefined") {
                        $scope.animal.breed = {breedUa: $scope.animal.breed};
                    }
                }

                return AnimalRegistrationFactory
                    .insertHomelessAnimal(animal)
                    .finally(function() {
                        //hide spinner loading
                        $scope.contentLoading--;

                        //clear fields
                        AnimalRegistrationValues.address = {
                            country:'',
                                town:'',
                                street:'',
                                index:''
                        };
                    });
            };

            $scope.submitForm = function(isValid){
                if(isValid){
                    $scope.insertHomelessAnimal($scope.animal);
                }
                else
                    alert('Невідома помилка.');
            };

            //Dependency injection
            AnimalHomelessRegController.$inject = ['$scope', 'AnimalRegistrationFactory', 'localStorageService',
                                                   'AnimalRegistrationValues', 'AnimalRegistrationConstants'];
        }
    );