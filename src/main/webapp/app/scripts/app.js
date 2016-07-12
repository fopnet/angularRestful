'use strict';

angular.module('ngdemoApp', [
    'ngRoute',
    'ngdemoApp.services',
    'ngdemoApp.controllers'
])
    .config(config)
    .run (run);

config.$inject = ['$routeProvider', '$httpProvider'];
function config($routeProvider, $httpProvider) {
    $routeProvider.when('/dummy', {templateUrl: 'views/user/dummy.html', controller: 'DummyCtrl'});
    $routeProvider.when('/login', {templateUrl: 'views/login/login.html', controller: 'LoginCtrl'});
    $routeProvider.when('/user-list', {templateUrl: 'views/user/user-list.html', controller: 'UserListCtrl'});
    $routeProvider.when('/user-detail/:id', {templateUrl: 'views/user/user-detail.html', controller: 'UserDetailCtrl'});
    $routeProvider.when('/user-creation', {templateUrl: 'views/user/user-creation.html', controller: 'UserCreationCtrl'});
    $routeProvider.when('/journal-detail/:id', {templateUrl: 'views/journal/journal-detail.html', controller: 'JournalDetailCtrl'});
    $routeProvider.when('/journal-creation', {templateUrl: 'views/journal/journal-creation.html', controller: 'JournalCreationCtrl'});
    $routeProvider.when('/journal-list', {templateUrl: 'views/journal/journal-list.html', controller: 'JournalListCtrl'});
    $routeProvider.when('/subscription-list', {templateUrl: 'views/journal/subscription-list.html', controller: 'SubscriptionCtrl'});
    $routeProvider.otherwise({redirectTo: '/login'});


    /* CORS... */
    /* http://stackoverflow.com/questions/17289195/angularjs-post-data-to-external-rest-api */
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.cache = false;
//    delete $httpProvider.defaults.headers.common['X-Requested-With'];
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
};

/* Global variable */
var app = angular.module('ngdemoApp.controllers', ['ui.bootstrap', 'ngCookies', 'base64']);
//
// http://stackoverflow.com/questions/14718826/angularjs-disable-partial-caching-on-dev-machine
run.$inject =  ['$rootScope','$templateCache', '$http', '$location', '$cookieStore'];
function run($rootScope, $templateCache, $http , $location, $cookieStore) {

    // Clear browser cache (in development mode)
    $rootScope.$on('$viewContentLoaded', function () {
        $templateCache.removeAll();
    });

    // keep user logged in after page refresh
    $rootScope.globals = $cookieStore.get('globals') || {};
    if ($rootScope.globals.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
    }

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        // redirect to login page if not logged in and trying to access a restricted page
        var restrictedPage = $.inArray($location.path(), ['/login', '/user-creation']) === -1;

        var loggedIn = $rootScope.globals.currentUser;
        if (restrictedPage && !loggedIn) {
            $location.path('/login');
        }
    });

};

