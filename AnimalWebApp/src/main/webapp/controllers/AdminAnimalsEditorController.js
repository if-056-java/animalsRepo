angular.module('AdminAnimalsEditor', ['AdminAnimalsModule', 'AdminAnimalsValues', 'angularFileUpload', 'nya.bootstrap.select', 'DPController'])
    .controller('AdminAnimalsEditorController', ['$scope', '$window', '$filter', '$routeParams', 'AdminAnimalsService', 'AdminAnimalsValues',
        function($scope, $window, $filter, $routeParams, AdminAnimalsService, AdminAnimalsValues) {

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 3;

            var animalId = $routeParams.animalId;                       //animal id
            $scope.animalImage = undefined;

            /**
             * @return list of animal types.
             */
            AdminAnimalsService.getAnimalTypes()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @return list of animal services.
             */
            AdminAnimalsService.getAnimalServices()
                .finally(function() {
                    $scope.contentLoading--;
                });

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            AdminAnimalsService.getAnimal(animalId)
                .finally(function() {
                    $scope.contentLoading--
                });

            $scope.$watch('contentLoading', function(newValue) {
                if (newValue != 0) {
                    return;
                }

                $scope.animalTypes = AdminAnimalsValues.animalTypes;        //list of animal types
                $scope.animalServices = AdminAnimalsValues.animalServices;  //list of animal services
                $scope.animal = AdminAnimalsValues.animal;                  //animal
            });

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.getAnimalBreeds = function() {
                $scope.filterAnimalBreedFlag = true;
                AdminAnimalsService.getAnimalBreeds($scope.animal.type.id)
                    .then(function(data) {
                        $scope.animalBreeds = data;
                    })
                    .finally(function() {
                        $scope.filterAnimalBreedFlag = false;
                    });
            }

            /**
             * update animal.
             */
            $scope.setAnimal = function() {
                $scope.animal.dateOfBirth = $filter('date')($scope.animal.dateOfBirth, 'yyyy-MM-dd');
                $scope.animal.dateOfSterilization = $filter('date')($scope.animal.dateOfSterilization, 'yyyy-MM-dd');
                $scope.animal.dateOfRegister = $filter('date')($scope.animal.dateOfRegister, 'yyyy-MM-dd');
                if (typeof $scope.animal.breed != "undefined") {
                    if (typeof $scope.animal.breed.id == "undefined") {
                        $scope.animal.breed = {breedUa: $scope.animal.breed};
                    }
                }

                AdminAnimalsService.updateAnimal($scope.animal)
                    .then(function(data) {
                        $window.location.href = "#/ua/user/home/animals/" + $scope.animal.id;
                    },
                    function(data) {
                        console.log('Animal update failed.')
                    });
            }
        }
    ])
    .controller('AdminAnimalsEditorSetImageController', ['$scope', 'FileUploader', function($scope, FileUploader) {
        var uploader = $scope.uploader = new FileUploader();

        uploader.autoUpload = true;         //Automatically upload files after adding them to the queue
        uploader.removeAfterUpload = true;  //Remove files from the queue after uploading

        /**
         * add filters.
         */
        uploader.filters.push({
            name: 'imageFilter',
            fn: function(item /*{File|FileLikeObject}*/, options) {
                var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        });

        /**
         * Fires before uploading an item.
         * @param item to be uploaded to the server
         */
        uploader.onBeforeUploadItem = function(item) {
            item.url = "/webapi/admin/animals/editor/upload/" + $scope.animal.id;
            item.formData = {animalId: $scope.animal.id};
        }

        /**
         * On file upload complete.
         * @param fileItem uploaded to the server
         * @param response of server
         * @param status response status
         * @param headers response headers
         */
        uploader.onCompleteItem = function(fileItem, response, status, headers) {
            if (status == 200) {
                $scope.animal.image = response.filePath;
                $scope.animalImage = response.filePath + "?timestamp=" + new Date().getTime();
            }
        };
    }]);
