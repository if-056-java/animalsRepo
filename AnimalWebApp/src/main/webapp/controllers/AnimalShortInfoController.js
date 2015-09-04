angular.module('AnimalShortInfoController', ['AnimalShortInfoService'])
	.controller('AnimalShortInfoController', ['$scope', 'AnimalShortInfoService',  '$routeParams', '$filter',
		function($scope, AnimalShortInfoService,  $routeParams, $filter) {

			var service = $routeParams.service;

			var animalId = $routeParams.animalId;

			$scope.animalImage = "resources/img/noimg.png";

			if(service === "found"){
				$scope.detail=false;
				$scope.textButton= $filter('translate')('CONTACT_AUTHOR');
				$scope.linkBack="#/ua/animal/found"
			};
			if(service === "adoption"){
				$scope.detail=true;
				$scope.textButton= $filter('translate')('ADOPT');
				$scope.linkBack="#/ua/animal/adoption";
			};
			if(service === "lost") {
				$scope.detail=true;
				$scope.textButton= $filter('translate')('CONTACT_OWNER');
				$scope.linkBack="#/ua/animal/lost";
			};

			$scope.openDialog = function(){
				$scope.showPopUpDialog = true;
			}

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
		}])
	.directive('popUpDialog', function(){
		return{
			restrict: 'E',
			scope: false,
			controller: function($scope){
				$scope.showPopUpDialog = false;
				$scope.closeDialog = function(){
					$scope.showPopUpDialog = false;
				}
			}
		}

	});