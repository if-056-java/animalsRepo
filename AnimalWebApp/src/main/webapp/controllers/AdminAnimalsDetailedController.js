angular.module('AdminAnimalsDetailed', ['AdminAnimalsModule', 'angularFileUpload', 'nya.bootstrap.select', 'DPController'])
    .controller('AdminAnimalsDetailedController', ['$scope', 'AdminAnimalsService', '$routeParams', '$window',
        function($scope, AdminAnimalsService, $routeParams, $window) {

            var animalId = $routeParams.animalId;   //route parameter - animal id
            var AADC = this;
            $scope.editor = false;
            $scope.animalImage = undefined;

            /**
             * back to the previous page.
             */
            $scope.goBack = function() {
                $window.history.back();
            }

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            this.getAnimal = function(animalId) {
                AdminAnimalsService.getAnimal(animalId)
                    .then(function(data) {
                        $scope.animal = data;
                        if (($scope.animal.image != undefined) && ($scope.animal.image.length > 0)) {
                            $scope.animalImage = $scope.animal.image + "?timestamp=" + new Date().getTime();
                        }
                    },
                    function(data) {
                        console.log('Animal retrieval failed.')
                    });
            };

            this.getAnimal(animalId);

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

            $scope.showEditor = function() {
                $scope.editor = true;
            }
        }])
    .controller('AdminAnimalsEditorController', ['$scope', 'AdminAnimalsService', '$filter',
        function($scope, AdminAnimalsService, $filter) {
            $scope.hideEditor = function() {
                $scope.$parent.editor = false;
                if (($scope.animal.image != undefined) && ($scope.animal.image.length > 0)) {
                    $scope.$parent.animalImage = $scope.$parent.animal.image + "?timestamp=" + new Date().getTime();
                }
            }

            /**
             * @return list of animal types and services.
             */
            $scope.$parent.$watch('editor', function(newValue, oldValue) {
                if (newValue === false || $scope.animalTypes != undefined) {
                    return;
                }

                AdminAnimalsService.getAnimalTypes()
                    .then(function (data) {
                        $scope.animalTypes = data;
                    },
                    function (data) {
                        console.log('Animal types retrieval failed.')
                    });

                if ($scope.animalServices != undefined) {
                    return;
                }

                AdminAnimalsService.getAnimalServices()
                    .then(function (data) {
                        $scope.animalServices = data;
                    },
                    function (data) {
                        console.log('Animal services retrieval failed.')
                    });
            });

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.$parent.$watch('animal.type', function() {
                if ($scope.$parent.animal == undefined) {
                    return;
                }
                $scope.animalBreeds = undefined;
                if ($scope.$parent.editor === true) {
                    $scope.$parent.animal.breed = undefined;
                }
                $scope.filterAnimalBreedFlag = true;
                AdminAnimalsService.getAnimalBreeds($scope.$parent.animal.type.id)
                    .then(function(data) {
                        $scope.animalBreeds = data;
                    },
                    function(data) {
                        console.log('Animal breeds retrieval failed.')
                    })
                    .finally(function() {
                        $scope.filterAnimalBreedFlag = false;
                    });

            });

            /**
             * update animal.
             */
            $scope.setAnimal = function() {
                $scope.$parent.animal.dateOfBirth = $filter('date')($scope.$parent.animal.dateOfBirth, 'yyyy-MM-dd');
                $scope.$parent.animal.dateOfSterilization = $filter('date')($scope.$parent.animal.dateOfSterilization, 'yyyy-MM-dd');
                $scope.$parent.animal.dateOfRegister = $filter('date')($scope.$parent.animal.dateOfRegister, 'yyyy-MM-dd');
                if (typeof $scope.$parent.animal.breed != "undefined") {
                    if (typeof $scope.$parent.animal.breed.id == "undefined") {
                        $scope.$parent.animal.breed = {breedUa: $scope.$parent.animal.breed};
                    }
                }

                AdminAnimalsService.updateAnimal($scope.$parent.animal)
                    .then(function(data) {
                        $scope.$parent.editor = false;
                        $scope.hideEditor();
                    },
                    function(data) {
                        console.log('Animal update failed.')
                    });
            }
        }])
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
                $scope.$parent.animal.image = response.filePath;
                $scope.$parent.animalImage = response.filePath + "?timestamp=" + new Date().getTime();
            }
        };
    }]);