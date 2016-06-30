'use strict';

app.controller('SubscriptionCtrl', ['$scope','$route', 'SubscriptionFactory' , '$location', '$window',
  function ($scope, $route, SubscriptionFactory, $location, $window) {

    $scope.setKeyName = function(keyname){
      $scope.sortKey = keyname;   //set the sortKey to the param passed
      $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };

    $scope.sortSubscriptions = function() {

        if (!$scope.sortKey)
            $scope.sortKey = "journal.subject";

        $scope.subscriptions.sort(function(a,b){
           return a[$scope.sortKey] - b[$scope.sortKey];
        });
    };

    /* callback for ng-click 'subscribe': */
    $scope.subscribe = function (subscription) {

      SubscriptionFactory.subscribe(subscription);

        $route.reload();
    };

    /* callback for ng-click 'subscribe': */
    $scope.unSubscribe = function (subscription) {

       SubscriptionFactory.unSubscribe({ id: subscription.journal.id });

        $route.reload();
    };

    $scope.readJournal = function (subscription) {

        $window.open(baseUrl + '/uploads/' + subscription.journal.fileName);
    };


    $scope.listSubscriptions = function() {

        // {id: $routeParams.id}
        SubscriptionFactory.query().then(function(result){
            $scope.subscriptions = result;
            $scope.totalItems = result.length;
            $scope.currentPage = 1;
            $scope.pageSize = 5;
            $scope.maxSize = Math.ceil($scope.totalItems / $scope.pageSize);

            $scope.sortSubscriptions();

            // http://stackoverflow.com/questions/25991393/how-to-update-my-total-items-in-angular-ui-pagination
            if(!$scope.$$phase){
                $scope.$apply();
            }
    })};

      // list journals to grid
    $scope.listSubscriptions();

  }]);
