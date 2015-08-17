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

animalRegistrationModule.controller('AnimalImageController',
    function AnimalImageController ($scope, FileUploader, $q, RESOURCES){

        var uploader = $scope.uploader = new FileUploader();

        uploader.autoUpload = true;         //Automatically upload files after adding them to the queue
        uploader.removeAfterUpload = true;  //Remove files from the queue after uploading

        /**
         * add filters.
         */
        uploader.filters.push({
            name: 'imageFilter',
            fn: function (item, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1 ? true : alert('Error');
            }
        });

        /**
         * Fires before uploading an item.
         * @param item to be uploaded to the server
         */
        uploader.onBeforeUploadItem = function (item) {
            item.url = RESOURCES.ANIMAL_REGISTRATION_IMAGE;
            console.log(item.url);
        };

        /**
         * On file upload complete.
         * @param fileItem uploaded to the server
         * @param response of server
         * @param status response status
         * @param headers response headers
         */
        uploader.onCompleteItem = function (fileItem, response, status, headers) {
            if (status === 200) {
                $scope.$parent.animal.image = response.filePath;
            }
        };

        //Dependency injection
        AnimalImageController.$inject = ['$scope', '$q', 'FileUploader'];
    });