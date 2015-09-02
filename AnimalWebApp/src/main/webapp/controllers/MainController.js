//created by 41X
var animalAppControllers = angular.module('MainController', []);

animalApp.controller('MainController', ['$scope', '$rootScope', 'localStorageService', 'userAccount',
    function($scope, $rootScope, localStorageService, userAccount) {

        if (!localStorageService.cookie.get("accessToken")) {
            localStorageService.clearAll();
        } else {
            if (localStorageService.get("memoryMe")=="ON")
                localStorageService.cookie.set("accessToken", localStorageService.get("accessToken"), 30);
            if (localStorageService.get("memoryMe")=="OFF")
                localStorageService.cookie.set("accessToken", localStorageService.get("accessToken"), 0.065);
        }


        $scope.logout = function() {
            userAccount.logout();
        };

        $scope.session = function(value) {

            if (!localStorageService.get("userName")){
                return false;
            } else {
                $scope.userRole = localStorageService.get("userRole");
                $rootScope.userName=localStorageService.get("userName");
                return true;
            }

        };

 }])
 
 //directive for password matching (StackOverFlow)   
.directive("passwordVerify", function() {
	   return {
	      require: "ngModel",
	      scope: {
	        passwordVerify: '='
	      },
	      link: function(scope, element, attrs, ctrl) {
	        scope.$watch(function() {
	            var combined;

	            if (scope.passwordVerify || ctrl.$viewValue) {
	               combined = scope.passwordVerify + '_' + ctrl.$viewValue; 
	            }                    
	            return combined;
	        }, function(value) {
	            if (value) {
	                ctrl.$parsers.unshift(function(viewValue) {
	                    var origin = scope.passwordVerify;
	                    if (origin !== viewValue) {
	                        ctrl.$setValidity("passwordVerify", false);
	                        return undefined;
	                    } else {
	                        ctrl.$setValidity("passwordVerify", true);
	                        return viewValue;
	                    }
	                });
	            }
	        });
	     }
	   };
	});

//initialize loading spinner
    //var targetContent = document.getElementById('loading-block');
    //new Spinner(opts).spin(targetContent);

/*
    $scope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
        if (toState.resolve) {
            console.log('b ' + $scope.contentLoading);
            $scope.contentLoading = 1;
            console.log('b ' + $scope.contentLoading);
        }
    });
    $scope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
        if (toState.resolve) {
            console.log('a ' + $scope.contentLoading);
            $scope.contentLoading = 0;
            console.log('a ' + $scope.contentLoading);
        }
    });
*/