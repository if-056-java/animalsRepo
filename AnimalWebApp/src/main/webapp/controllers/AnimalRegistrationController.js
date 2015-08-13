/**
 * Created by oleg on 13.08.2015.
 */
adoptionModule
    .controller('AnimalRegistrationController',
        function AnimalRegistrationController($scope, AnimalRegistrationFactory) {

            $scope.animal = {};

            //This variable include info about address
            $scope.address = {
                country:undefined,
                town:undefined,
                street:undefined,
                index:undefined
            };

            //Insert homeless animal
            $scope.insertHomelessAnimal = function (animal) {

                $scope.animal.address = $scope.address.country.concat(' ' + $scope.address.town + ' ' + $scope.address.street + ' ' + $scope.address.index);

                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth()+1; //January is 0!
                var yyyy = today.getFullYear();

                $scope.animal.dateOfRegister = yyyy + '-' + mm + '-' + dd;

                return AnimalRegistrationFactory
                    .insertHomelessAnimal(animal)
                    .then(
                    function (data) {
                        animal = $scope.animals;
                    },
                    function (data) {
                        console.log('Error.' + data)
                    }
                    ).finally(function() {
                        console.log(animal);
                        console.log($scope.animal);
                    });
            };

            //Dependency injection
            AnimalRegistrationController.$inject = ['$scope', 'AnimalRegistrationFactory'];
        }
    )
    .controller('AnimalDetailController',
        function AnimalDetailController($scope, AnimalDetailFactory) {

            this.getAnimalTypes = function() {
                AnimalDetailFactory.getAnimalTypes()
                    .then(function(data) {
                        $scope.animalTypes = data;
                    },
                    function(data) {
                        console.log('Animal retrieval failed.')
                    });
            };

            this.getAnimalTypes();

            $scope.getAnimalBreeds = function() {
                AnimalDetailFactory.getAnimalBreeds($scope.$parent.animal.type.id)
                    .then(function(data) {
                        $scope.animalBreeds = data;
                    },
                    function(data) {
                        console.log('Animal breeds retrieval failed.')
                    })
                    .finally(function() {
                    });
            };

/*
            $scope.colors = [
                    'Білий',
                    'Сірий',
                    'Чорний',
                    'Рудий',
                    'Коричневий',
                    'Палевий',
                    'Жовтий',
                    'Червоний',
                    'Синій',
                    'Зелений',
                    'Оранжевий',
                    'Салатовий'
                ];
*/
            $scope.colorTypes = [
                'Білий',
                'Сірий',
                'Чорний',
                'Рудий',
                'Коричневий',
                'Палевий',
                'Підпалий',
                'Вовчий',
                'Плямистий',
                'Мармуровий',
                'Черпачний',
                'Триколірний',
                'Рябий',
                'Тигровий'
            ];

            $scope.addColor = function (color) {
                $scope.$parent.animal.color = color;
            };

            //Dependency injection
            AnimalDetailController.$inject = ['$scope', 'AnimalDetailFactory'];
        }
    );
