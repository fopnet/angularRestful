'use strict';

var subscriptionUrl = baseUrl + '/web/subscriptions';

services.factory('SubscriptionFactory', ['$resource','$http','$log','$q', function ($resource, $http, $log, $q) {

    return {
        query: function(param) {
            return $resource(subscriptionUrl, {}, {
                query: { method: 'GET', isArray: true }
            }).query().$promise;
        },
        subscribe: function(subscription) {
            return $resource(subscriptionUrl, {}, {
                create :{ method: 'POST' }
            }).create(subscription).$promise;
        },
        unSubscribe: function(param) {
            return $resource(subscriptionUrl + '/:id', {}, {
                delete: { method: 'DELETE', params: {id: '@id'} }
            }).delete(param).$promise;
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