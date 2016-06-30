'use strict';

var userUrl = baseUrl + '/ngdemo/web/users';

services.factory('DummyFactory', function ($resource) {
    return $resource(baseUrl + '/ngdemo/web/dummy', {}, {
        query: { method: 'GET', params: {} }
    })
});

services.factory('UserFactory', function ($resource) {
    return {
        show:function(param) {
            return $resource(userUrl + '/:id', {}, {
                show: { method: 'GET' }
            }).show(param);
        },
        update:function(param) {
            return $resource(userUrl + '/:id', {}, {
                update: { method: 'PUT', params: {id: '@id'} }
            }).update(param).$promise;
        },
        delete:function(param) {
            return $resource(userUrl + '/:id', {}, {
                delete: { method: 'DELETE', params: {id: '@id'} }
            }).delete(param).$promise;
        },
        getSubscriberName : function(user) {
            if (user != undefined) {
                return  user.firstName + ' ' + user.lastName ;
            }
            return '???';
        }
    }
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
