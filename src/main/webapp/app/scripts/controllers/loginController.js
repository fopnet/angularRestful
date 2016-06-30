'use strict';

app.controller('LoginCtrl', ['$scope', '$location', 'UserFactory',
  function ($scope, $location, UserFactory ) {
      var vm = this;

      vm.dataLoading = false;

     ($scope.initController = function() {
          // reset login status
          // AuthenticationService.ClearCredentials();
     });

    $scope.login = function () {
        vm.dataLoading = true;

        /*AuthenticationService.Login(vm.username, vm.password, function (response) {
            if (response.success) {
                AuthenticationService.SetCredentials(vm.username, vm.password);
                $location.path('/');
            } else {
                FlashService.Error(response.message);
                vm.dataLoading = false;
            }
        });*/
    };

    /* callback for ng-click 'createJournal': */
    $scope.register = function () {
      $location.path('/user-creation');
    };

  }]);