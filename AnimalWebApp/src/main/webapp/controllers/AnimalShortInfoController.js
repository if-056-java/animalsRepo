angular.module('AnimalShortInfoController', ['AnimalShortInfoService'])
    .controller('AnimalShortInfoController', ['$scope', 'AnimalShortInfoService',  '$routeParams', '$filter', 
        function($scope, AnimalShortInfoService,  $routeParams, $filter) {
			
			var service = $routeParams.service;
			
			var animalId = $routeParams.animalId;
			
			if(service === "found"){
			    $scope.detail=false;
				$scope.textButton= $filter('translate')('CONTACT_AUTHOR');
				$scope.linkButton="#";
				$scope.linkBack="#/ua/animal/found"
			};
			if(service === "adoption"){
			    $scope.detail=true;
				$scope.textButton= $filter('translate')('ADOPT');
				$scope.linkButton="#";
				$scope.linkBack="#/ua/animal/adoption";
			};
			if(service === "lost") {
			    $scope.detail=true;
				$scope.textButton= $filter('translate')('CONTACT_OWNER');
				$scope.linkButton="#";
				$scope.linkBack="#/ua/animal/lost";
			};

            this.getAnimal = function(service, animalId) {
                AnimalShortInfoService.getAnimal(service, animalId)
                    .then(function(data) {
                        $scope.animal = data;
                    },
                    function(data) {
                        console.log('Animal retrieval failed.');
                    });
            };
            this.getAnimal(service, animalId);
    }]);