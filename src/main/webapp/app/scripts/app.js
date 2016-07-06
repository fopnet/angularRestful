'use strict';

angular.module('ngdemoApp', [
  'ngRoute',
  'ngLocale',
  'ngdemoApp.services',
  'ngdemoApp.controllers'
  ])
.config(function ($routeProvider, $httpProvider) {
        $routeProvider.when('/dummy', {templateUrl: 'views/user/dummy.html', controller: 'DummyCtrl'});
        $routeProvider.when('/login', {templateUrl: 'views/login/login.html', controller: 'LoginCtrl'});
        $routeProvider.when('/user-list', {templateUrl: 'views/user/user-list.html', cacheable:false, controller: 'UserListCtrl'});
        $routeProvider.when('/user-detail/:id', {templateUrl: 'views/user/user-detail.html', controller: 'UserDetailCtrl'});
        $routeProvider.when('/user-creation', {templateUrl: 'views/user/user-creation.html', controller: 'UserCreationCtrl'});
        $routeProvider.when('/journal-detail/:id', {templateUrl: 'views/journal/journal-detail.html', controller: 'JournalDetailCtrl'});
        $routeProvider.when('/journal-creation', {templateUrl: 'views/journal/journal-creation.html', controller: 'JournalCreationCtrl'});
        $routeProvider.when('/journal-list', {templateUrl: 'views/journal/journal-list.html', controller: 'JournalListCtrl'});
        $routeProvider.when('/subscription-list', {templateUrl: 'views/journal/subscription-list.html', controller: 'SubscriptionCtrl'});
        $routeProvider.otherwise({redirectTo: '/login'});
/*
 $router.config([
 {path:'/', component:{default:'home'}}
 ]);

*/

  /* CORS... */
  /* http://stackoverflow.com/questions/17289195/angularjs-post-data-to-external-rest-api */
  $httpProvider.defaults.useXDomain = true;
  delete $httpProvider.defaults.headers.common['X-Requested-With'];
});


/* Global variable */

var app = angular.module('ngdemoApp.controllers', ['ui.bootstrap', 'ngLocale']);

// Clear browser cache (in development mode)
//
// http://stackoverflow.com/questions/14718826/angularjs-disable-partial-caching-on-dev-machine
app.run(function ($rootScope, $templateCache) {
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });
});

