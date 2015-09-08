angular.module('AnimalShortInfoController', ['AnimalShortInfoService'])
	.controller('AnimalShortInfoController', ['$scope', 'AnimalShortInfoService',  '$routeParams', '$filter',
		function($scope, AnimalShortInfoService,  $routeParams, $filter) {

			var service = $routeParams.service;

			var animalId = $routeParams.animalId;

			$scope.animalImage = "resources/img/no_img.png";

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

			this.getAnimal = function(animalId) {
				AnimalShortInfoService.getAnimal(animalId)
					.then(function(data) {
						$scope.animal = data;
						if ($scope.animal.image != undefined) {
							if ($scope.animal.image.length > 0) {
								$scope.animalImage = $scope.animal.image;
							}
						}
					},
					function(data) {
						console.log('Animal retrieval failed.');
					});
			};
			this.getAnimal(animalId);
		}]);