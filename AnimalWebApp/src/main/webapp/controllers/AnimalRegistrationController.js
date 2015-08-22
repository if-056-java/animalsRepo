/**
 * Created by oleg on 13.08.2015.
 */
animalRegistrationModule
    .controller('AnimalRegistrationController',
        function AnimalRegistrationController($scope, AnimalRegistrationFactory) {

            //Current date
            var currentDate = function(){
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth(); //January is 0!
                var yyyy = today.getFullYear();

                return new Date(Date.UTC(yyyy, mm, dd));
            };

            var imageFile = null;

            $scope.animal = {
                dateOfRegister: currentDate(),
                service: {
                    id: 2,
                    service: "знайдена"
                },
                active: true
            };

            //This variable include info about address
            $scope.address = {
                country:undefined,
                town:undefined,
                street:undefined,
                index:undefined
            };

            //Insert homeless animal
            $scope.insertHomelessAnimal = function (animal) {

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
                    .then(
                    function (data) {
                        animal = $scope.animal;
                    },
                    function (data) {
                        console.log('Error.' + data)
                    }
                    ).finally(function() {
                        console.log($scope.animal);
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
            AnimalRegistrationController.$inject = ['$scope', 'AnimalRegistrationFactory'];
        }
    )
    .controller('AnimalDetailController', function AnimalDetailController($scope, AnimalDetailFactory) {

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

            $scope.imageCropResult = null;
            $scope.showImageCropper = false;

            //Dependency injection
            AnimalDetailController.$inject = ['$scope', 'AnimalDetailFactory'];
        }
    )
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

    }]
    )
    .controller('AnimalImageController', function AnimalImageController ($scope){

        //Loaded image
        $scope.myImage='';

        //Zoomed image
        $scope.myCroppedImage='';

        /**
         * Convert image to base64 format
         * @param image
         */
        var getBase64FromImage = function(image){
            var first = image.indexOf(',') + 1;
            var size = image.length;
            $scope.$parent.animal.image = image.substr(first, size);
        };

        /**
         * Handle file select
         * @param evt
         */
        var handleFileSelect = function(evt) {
            var file = evt.currentTarget.files[0];
            var reader = new FileReader();
            reader.onload = function (evt) {
                $scope.$apply(function($scope){
                    $scope.myImage=evt.target.result;
                });
            };
            reader.readAsDataURL(file);
        };
        angular.element(document.querySelector('#fileInput')).on('change',handleFileSelect);

        /**
         * Watch on image zoom.
         */
        $scope.$watch('myCroppedImage', function(newVal) {
           if(newVal)
               getBase64FromImage($scope.myCroppedImage);
        });

        //Dependency injection
        AnimalImageController.$inject = ['$scope'];
    });