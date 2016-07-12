'use strict';

app.controller('JournalListCtrl', ['$scope', '$timeout', 'JournalsFactory', 'JournalFactory', 'UserFactory', '$location',
  function ($scope, $timeout, JournalsFactory, JournalFactory, UserFactory, $location) {

    /* callback for ng-click 'editJournal': */
    $scope.editJournal = function (journalId) {
       $scope.journals = [];

        $timeout(function() {
            $location.path('/journal-detail/' + journalId);
        }, 0);
    };

    /* callback for ng-click 'deleteJournal': */
    $scope.deleteJournal = function (journalId) {
      JournalFactory.delete({ id: journalId });
      this.listJournals();
    };

    /* callback for ng-click 'createJournal': */
    $scope.createNewJournal = function () {
      $location.path('/journal-creation');
    };

    $scope.pageChanged = function() {
        //$log.log('Page changed to: ' + $scope.currentPage);
    };

    $scope.setKeyName = function(keyname){
      $scope.sortKey = keyname;   //set the sortKey to the param passed
      $scope.reverse = !$scope.reverse; //if true make it false and vice versa
    };

    $scope.sortJournals = function() {

        if (!$scope.sortKey)
            $scope.sortKey = "subsject";

        $scope.journals.sort(function(a,b){
           return a[$scope.sortKey] - b[$scope.sortKey];
        });
    };

    $scope.getSubscriberName = function(user) {
        return UserFactory.getSubscriberName(user);
    };

    $scope.listJournals = function() {

        JournalsFactory.query().then(function(result){
            $scope.journals = result;
            $scope.totalItems = result.length;
            $scope.currentPage = 1;
            $scope.pageSize = 5;
            $scope.maxSize = Math.ceil($scope.totalItems / $scope.pageSize);

            $scope.sortJournals();

        });
    };

      // list journals to grid
    $scope.listJournals();

  }]);


app.controller('JournalDetailCtrl', ['$scope', '$routeParams', 'JournalFactory', 'UserFactory', '$location',
    function ($scope, $routeParams, JournalFactory, UserFactory, $location) {

        /* callback for ng-click 'updateJournal': */
        $scope.updateJournal = function () {
            var file = $("#fileInput")[0].files[0];

            JournalFactory.update($scope.journal, file).then(function(){
                $location.path('/journal-list');
            });

        };

        /* callback for ng-click 'cancel': */
        $scope.cancel = function () {
            $location.path('/journal-list');
        };

        $scope.getSubscriberName = function(user) {
            return UserFactory.getSubscriberName(user);
        };

        $scope.journal = JournalFactory.show({id: $routeParams.id});
    }]);

app.controller('JournalCreationCtrl', ['$scope', 'JournalsFactory', '$location',
  function ($scope, JournalsFactory, $location) {
//    $scope.localeID = $locale.ID;

    /* callback for ng-click 'createNewJournal': */
    $scope.createNewJournal = function () {
      if ($scope.journalForm.$invalid)
        return;

      var file = $("#fileInput")[0].files[0];
      JournalsFactory.create($scope.journal, file)
                    .then(function (response) {
                          //successful
                      }, function (error) {
                          //error
                          console.error("Error " + error);
                      });

      $location.path('/journal-list');
    };

     $scope.showFileName = function(event) {
        console.log(event);
     };

  }]).directive('validFile',function(){
    return {
        require:'ngModel',
        link:function(scope,el,attrs,ngModel){
            //change event is fired when file is selected
            el.bind('change',function(){
                scope.$apply(function(){
                    ngModel.$setViewValue(el.val());
                    ngModel.$render();
                })
            })
        }
    }});
