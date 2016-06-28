'use strict';

var services = angular.module('ngdemoApp.services', ['ngResource']);

var baseUrl = 'http://localhost:8080';
var userUrl = baseUrl + '/ngdemo/web/users';

services.factory('DummyFactory', function ($resource) {
    return $resource(baseUrl + '/ngdemo/web/dummy', {}, {
        query: { method: 'GET', params: {} }
    })
});

services.factory('UserFactory', function ($resource) {
    return $resource(userUrl + '/:id', {}, {
        show: { method: 'GET' },
        update: { method: 'PUT', params: {id: '@id'} },
        delete: { method: 'DELETE', params: {id: '@id'} }
    })
});

services.factory('UsersFactory', ['$resource','$http','$log','$q', function ($resource, $http, $log, $q) {
    return {
        query: function() {
            return $resource(userUrl, {}, {
                query: { method: 'GET', isArray: true }
            }).query().$promise;
        },
        create: function(user) {
           $resource(userUrl, {}, {
                create :{ method: 'POST' }
            }).create(user).$promise;
        },
        numberOfUsers : function () {
            // {headers: { 'Accept': 'text/plain' }}
            return $http.get(userUrl + '/numberOfUsers', {})
                .then(function (response) {
                    return response.data;
                })
                .catch(this.logError)
                ;
        },
        logError : function (response) {
            $log.error('ngdemo: XHR status:' + response.status + ' erro:' + response.data);

            var message = 'Ocorreu um erro ';
            if (response.config != null)
                message += 'ao chamar ' + response.config.url + ' , please try again';
            else
                message += response.message;

            // alert(message);

            return $q.reject(message);
        }

}}]);

/*
    return $resource(userUrl, {}, {
        query: { method: 'GET', isArray: true },
        create: { method: 'POST' }
    })
*/

/*services.factory('UsersFactory', function ($resource) {
    return $resource(baseUrl + '/ngdemo/web/users/numberOfUsers', {headers: { 'Accept': 'text/plain' }} , {
        numberOfUsers: { method: 'GET', isArray: false }
    })
});*/


//---------------------------------------------------------------------------------------------------
/*

services.service('UserService',['$http','$log','$q', UserService]);

function UserService($http, $log, $q) {

    this.numberOfUsers = function () {
        // {headers: { 'Accept': 'text/plain' }}
        return $http.get(userUrl + '/getNumberOfUsers', {} )
            .then(function (response) {
                return response.data;
            })
            .catch(this.logError)
            ;

    };

    this.logError = function (response) {
        $log.error('ngdemo: XHR status:' + response.status + ' erro:' + response.data);

        var message = 'Ocorreu um erro ';
        if (response.config != null)
            message += 'ao chamar ' + response.config.url + ' , please try again';
        else
            message += response.message;

        // alert(message);

        return $q.reject(message);
    };
}
*/
