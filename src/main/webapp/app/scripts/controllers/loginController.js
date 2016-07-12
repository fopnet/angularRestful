'use strict';

app.controller('LoginCtrl', ['$scope','$rootScope', '$location', '$base64', 'UserFactory', 'AuthenticationService', 'FlashService',
  function ($scope, $rootScope, $location, $base64, UserFactory , AuthenticationService, FlashService) {
      var vm = this;

      $scope.dataLoading = false;
      $scope.email = '';
      $scope.password = '';

     ($scope.initController = function() {
          // reset login status
          AuthenticationService.ClearCredentials();
     });

    $scope.login = function () {
        $scope.dataLoading = true;

        AuthenticationService.Login($scope.email, $scope.password,
            function (response) {
                $location.path('/dummy/');
            },
            function (response) {
                $scope.dataLoading = false;
                AuthenticationService.ClearCredentials();

                FlashService.Error(response);
            }
        );
    };

    /* callback for ng-click 'createJournal': */
    $scope.register = function () {
      $location.path('/user-creation');
    };

    $scope.logout = function() {
        AuthenticationService.Logout().then(function(){
            $location.path("/login");
        })
    };

  }]);