angular.module('AnimalShortInfoController', ['AnimalShortInfoService'])
    .controller('AnimalShortInfoController', ['$scope', 'AnimalShortInfoService',  '$routeParams', 
        function($scope, AnimalShortInfoService,  $routeParams) {
			
			var service = $routeParams.service;
			
			var animalId = $routeParams.animalId;
			
			$scope.service = service;
			
			if(service === "found"){
			    $scope.detail=false;
				$scope.textButton="";
				$scope.linkButton="#"
			}
			if(service === "adoption"){
			    $scope.detail=true;
				$scope.textButton="Adopt";
				$scope.linkButton="#"
				
			}
			if(service === "lost") {
			    $scope.detail=true;
				$scope.textButton="";
				$scope.linkButton="#"
			}

            this.getAnimal = function(service, animalId) {
                AnimalShortInfoService.getAnimal(service, animalId)
                    .then(function(data) {
                        $scope.animal = data;
                    },
                    function(data) {
                        console.log('Animal retrieval failed.')
                    });
            };
            this.getAnimal(service, animalId);
    }]);