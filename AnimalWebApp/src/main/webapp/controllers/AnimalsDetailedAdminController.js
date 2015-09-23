angular.module('AnimalsDetailedAdminController', ['AnimalsAdminModule', 'AnimalsAdminValues'])
    .controller('AnimalsDetailedAdminController', ['$scope', '$routeParams', '$window', 'AnimalsAdminService',
        'AnimalsAdminValues', '$filter',
        function($scope, $routeParams, $window, AnimalsAdminService, AnimalsAdminValues, $filter) {

            AnimalsAdminService.rolesAllowed("moderator");

            //initialize loading spinner
            var targetContent = document.getElementById('loading-block');
            new Spinner(opts).spin(targetContent);
            //This variable decides when spinner loading for contentis closed.
            $scope.contentLoading = 0;

            $scope.currentLanguage = $window.localStorage.getItem('NG_TRANSLATE_LANG_KEY');

            var animalId = $routeParams.animalId;       //animal id
            $scope.animal = AnimalsAdminValues.animal;  //animal
            $scope.animalImage = undefined;
            $scope.errors = [];

            /**
             * @param animalId id of animal used for lookup.
             * @return animal instance.
             */
            var getAnimal = function() {
                $scope.contentLoading++;

                AnimalsAdminService.getAnimal(animalId)
                    .then(function (respounce) {
                        $scope.animalImage = "resources/img/no_img.png";
                        if (AnimalsAdminValues.animal.image != undefined) {
                            if (AnimalsAdminValues.animal.image.length > 0) {
                                $scope.animalImage = AnimalsAdminValues.animal.image;
                            }
                        }

                        if ($scope.animal.user == undefined) {
                            $scope.error = $filter('translate')("ERROR_NO_RECORDS");
                        }
                    }, function (respounce) {
                        $scope.errors.push({msg: $filter('translate')("ERROR_ANIMAL_NOT_FOUND")});
                    })
                    .finally(function () {
                        $scope.contentLoading--;
                    });
            }

            getAnimal();

            /**
             * delete animal.
             */
            $scope.deleteAnimal = function() {
                if (!confirm($filter('translate')("ANIMAL_DETAILED_CONFIRM_DELETE"))) {
                    return;
                }

                $filter('date')($scope.animal.dateOfRegister, 'yyyy-MM-dd')
                AnimalsAdminService.deleteAnimal($scope.animal.id)
                    .then(function(data) {
                        $window.location.href = "#/ua/user/home/animals";
                    },
                    function(data) {
                        $window.alert($filter('translate')("ANIMAL_DETAILED_DELETE_FAILED"));
                    });
            }

            /**
             * Convert an image
             * to a base64 url
             */
            function convertImgToBase64URL(){
                var img = document.getElementById("animalImg");
                var canvas = document.createElement('canvas');
                canvas.width = img.clientWidth;
                canvas.height = img.clientHeight;
                canvas.getContext('2d').drawImage(img, 1, 1);
                console.log(canvas.toDataURL());
                return canvas.toDataURL();
            }

            $scope.toPdf = function() {
                var docDefinition = { content: [
                    { text: 'Пропала ' + $scope.animal.type.type, fontSize: 21, alignment: 'center' },
                    { text: ' ' },
                    { image: convertImgToBase64URL(), alignment: 'center', fit: [500, 500] },
                    { text: ' ' },
                    { text: 'Прохання звертатись за телефоном: ' + $scope.animal.user.phone, fontSize: 21, alignment: 'center' }
                ] };

                pdfMake.createPdf(docDefinition).open();
            }

        }]);