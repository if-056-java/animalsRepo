/**
 * Created by oleg on 31.08.2015.
 */
animalApp
    .controller('TranslateController',
        function TranslateController($scope, $rootScope, $translate, $route) {
            $scope.changeLanguage = function (langKey) {
                $translate.use(langKey);
                $route.reload();
            };
        }
    );
