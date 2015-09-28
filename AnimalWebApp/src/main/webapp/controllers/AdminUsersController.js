adminUsersModule
    .controller('AdminUsersController',
    function AdminUsersController($scope, AdminUsersFactory, AdminUsersValues, localStorageService, $location) {

    	 if (localStorageService.get('userRole')!=="moderator"){
    			$location.path("#ua");	
    		}
    	 
        //initialize loading spinner
        var targetContent = document.getElementById('loading-block');
        new Spinner(opts).spin(targetContent);

        $scope.contentLoading = 0;
        $scope.errorMessage = '';

        //Pages
        $scope.filter = AdminUsersValues.filter;            //filter
        $scope.totalItems = AdminUsersValues.totalItems;    //table rows count
        $scope.users = AdminUsersValues.users;          //animal instance

        /**
         * @return count of rows for pagination.
         */
        $scope.contentLoading++;
        AdminUsersFactory.getAmountRecords()
            .then(
            function(result){},

            //fail
            function(error){
                $scope.totalItems.count = 0;
                $scope.errorMessage = error;
            }
        )
            .finally(function() {
                $scope.contentLoading--;
            });

        $scope.contentLoading++;
        AdminUsersFactory.getListOfAdminUsers()
            .then(
            function(result){},

            //fail
            function(error){
                $scope.errorMessage = error;
            }
        )
            .finally(function() {
                $scope.contentLoading--;
            });

        /**
         * @return next page.
         */
        $scope.pageChanged = function() {
            $scope.contentLoading++;

            //scroll to top of the page
            jQuery('html, body').animate({ scrollTop: 0 }, 500);

            AdminUsersFactory.getListOfAdminUsers()
                .finally(function() {
                    $scope.contentLoading--;
            });
        };

        /**
         * @return list of users with given count of rows.
         */
        $scope.countChanged = function(count) {
            $scope.filter.limit = count;
            AdminUsersFactory.getListOfAdminUsers();
        };

        //Dependency injection
        AdminUsersController.$inject = ['$scope', 'AdminUsersFactory', 'AdminUsersValues', 'localStorageService'];

    }).controller('AdminUsersFilter',
    function AdminUsersFilter($scope, AdminUsersFactory, AdminUsersValues, $window) {

        //locale
        $scope.currentLanguage = $window.localStorage.getItem('NG_TRANSLATE_LANG_KEY');

        $scope.filter = AdminUsersValues.filter;    //filter

        /**
         * @return list of user types.
         */
        AdminUsersFactory.getUserTypes()
            .then(
            function(data){
                $scope.userTypes = data;
            },
            function(error){
                $scope.errorMessage = error;
            }
        )
            .finally(function() {
            });

        /**
         * @return list of user roles.
         */
        AdminUsersFactory.getUserRoles()
            .then(
            function(data){
                $scope.userRoles = data;                
            },
            function(error){
                $scope.errorMessage = error;
            }
        )
            .finally(function() {
            });

        /**
         * reset filter values.
         */
        $scope.reset = function() {
            $scope.filter.user.isActive = undefined;
            $scope.filter.user.userRole = undefined;
            $scope.filter.user.userType = undefined;

            $scope.doFilter();
            jQuery('html, body').animate({ scrollTop: 0 }, 500);
        };

        /**
         * @return list of users according to filter values.
         */
        $scope.doFilter = function() {
            AdminUsersFactory.getAmountRecords()
                .then(
                function(result){},

                //fail
                function(error){
                    $scope.totalItems.count = 0;
                    $scope.errorMessage = error;
                });

            $scope.contentLoading++;
            AdminUsersFactory.getListOfAdminUsers().finally(
                function(){
                    $scope.contentLoading--;
                }
            );
            jQuery('html, body').animate({ scrollTop: 0 }, 500);
        };

        //Dependency injection
        AdminUsersFilter.$inject = ['$scope', 'AdminUsersFactory', 'AdminUsersValues', '$window'];
    }
);