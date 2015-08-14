angular.module('AdminAnimalsEditor', ['nya.bootstrap.select', 'ui.bootstrap', 'angularFileUpload', 'AdminAnimalsModule'])
    .controller('AdminAnimalsEditorController', ['$scope', 'AdminAnimalsService', '$routeParams', '$window', '$filter', '$modal',
        function($scope, AdminAnimalsService, $routeParams, $window, $filter, $modal) {
            /**
             * back to the previous page.
             */
            $scope.goBack = function() {
                $window.history.back();
            }

            /**
             * @return list of animal types.
             */
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

            var animalId = $routeParams.animalId; //route parameter - animal id

            /**
             * @return list of animal breeds according to animal type.
             */
            $scope.getAnimalBreeds = function() {
                $scope.animalBreeds = undefined;
                $scope.filterAnimalBreedFlag = true;
                AdminAnimalsService.getAnimalBreeds($scope.animal.type.id)
                    .then(function(data) {
                        $scope.animalBreeds = data;
                    },
                    function(data) {
                        console.log('Animal breeds retrieval failed.')
                    })
                    .finally(function() {
                        $scope.filterAnimalBreedFlag = false;
                    });

            }

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            this.getAnimal = function(animalId) {
                AdminAnimalsService.getAnimal(animalId)
                    .then(function(data) {
                        $scope.animal = data;
                        if ($scope.animal.image != undefined) {
                            $scope.animalImage = $scope.animal.image + "?timestamp=" + new Date().getTime();
                        }
                        $scope.getAnimalBreeds();
                    },
                    function(data) {
                        console.log('Animal retrieval failed.')
                    });

            };

            this.getAnimal(animalId);

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

            $scope.setImage = function () {
                var modalInstance = $modal.open({
                    animation: true,
                    templateUrl: 'setImage.html',
                    controller: 'AdminAnimalsEditorModalController',
                    resolve: {
                        items: function () {
                            return $scope.items;
                        }
                    }
                });

                modalInstance.result.then(function (selectedItem) {
                    $scope.selected = selectedItem;
                }, function () {
                    console.log('Modal dismissed at: ' + new Date());
                });
            };
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
            item.url = "/webapi/animal/image/" + $scope.animal.id;
            item.formData = {animalId: $scope.animal.id};
        };

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
                $scope.$parent.animalImage = response.filePath + "?timestamp=" + new Date().getTime();
                console.log($scope.animalImage);
            }
        };
    }])
    .controller('AdminAnimalsEditorModalController', ['$scope', function($scope, $modalInstance) {
        $scope.ok = function () {
            $modalInstance.close($scope.selected.item);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    }]);