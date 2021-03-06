'use strict';

app.controller('DummyCtrl', ['$scope', 'DummyFactory', function ($scope, DummyFactory) {
  $scope.bla = 'bla from controller';
  DummyFactory.query({}, function (data) {
    $scope.foo = data.firstName;
  })
}]);

// Filter provider
app.filter("startFrom",function(){
    return function(input, start){
        if (input) {
            return input.slice(start);
        }
        return [];
    }
});

app.controller('UserListCtrl', ['$scope', '$timeout', 'UsersFactory', 'UserFactory', '$location',
  function ($scope, $timeout, UsersFactory, UserFactory, $location) {

    /* callback for ng-click 'editUser': */
    $scope.editUser = function (userId) {
        $scope.users = [];

        $timeout(function() {
            $location.path('/user-detail/' + userId);
        }, 0);

    };

    /* callback for ng-click 'deleteUser': */
    $scope.deleteUser = function (userId) {
      UserFactory.delete({ id: userId });
      this.listUsers();
    };

    /* callback for ng-click 'createUser': */
    $scope.createNewUser = function () {
      $location.path('/user-creation');
    };

    $scope.pageChanged = function() {
        //$log.log('Page changed to: ' + $scope.currentPage);
    };

    $scope.setKeyName = function(keyname){
      $scope.sortKey = keyname;   //set the sortKey to the param passed
      $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };

    $scope.sortUsers = function() {

        if (!$scope.sortKey)
            $scope.sortKey = "firstName";

        $scope.users.sort(function(a,b){
           return a[$scope.sortKey] - b[$scope.sortKey];
        });
    };


    $scope.listUsers = function() {

        UsersFactory.query().then(function(result){
            $scope.users = result;
            $scope.totalItems = result.length;
            $scope.currentPage = 1;
            $scope.pageSize = 5;
            $scope.maxSize = Math.ceil($scope.totalItems / $scope.pageSize);

            $scope.sortUsers();
        });
    };

     $scope.listUsers();

  }]);

app.controller('UserDetailCtrl', ['$scope', '$routeParams', 'UserFactory', '$location',
  function ($scope, $routeParams, UserFactory, $location) {

    /* callback for ng-click 'updateUser': */
    $scope.updateUser = function () {
        UserFactory.update($scope.user).then(function(){
            $location.path('/user-list/');
        });

    };

    /* callback for ng-click 'cancel': */
    $scope.cancel = function () {
      $location.path('/user-list');
    };

    $scope.user = UserFactory.show({id: $routeParams.id});
  }]);

app.controller('UserCreationCtrl', ['$scope', 'UsersFactory', '$location',
  function ($scope, UsersFactory, $location) {

    /* callback for ng-click 'createNewUser': */
    $scope.createNewUser = function () {
      if ($scope.userForm.$invalid)
        return;

      UsersFactory.create($scope.user);
      $location.path('/user-list');
    }
  }]);

