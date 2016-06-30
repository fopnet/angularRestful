'use strict';

var journalUrl = baseUrl + '/ngdemo/web/journals';

function getFormData (journal, file) {
    var fdata = new FormData();
    fdata.append("journal", new Blob([angular.toJson(journal)], {type: "application/json"}));
//    if (file != undefined)
        fdata.append("file", file);

    return fdata;

};

services.factory('JournalsFactory', ['$resource','$http','$log','$q', function ($resource, $http, $log, $q) {
    return {
        query: function() {
            return $resource(journalUrl, {}, {
                query: { method: 'GET', isArray: true }
            }).query().$promise;
        },
        create: function(journal, file) {

            var fdata = getFormData(journal, file);

            return $resource(journalUrl, { }, {
                postWithFile: {
                    method: "POST",
                    params: fdata,
                    transformRequest: angular.identity,
                    headers: { 'Content-Type': undefined }
                }
            }).postWithFile(fdata).$promise;

        },
        numberOfUsers : function () {
            // {headers: { 'Accept': 'text/plain' }}
            return $http.get(journalUrl + '/numberOfUsers', {})
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



services.factory('JournalFactory', function ($resource) {

    return {
        show: function(param) {
            return $resource(journalUrl + '/:id', {}, {
                show: { method: 'GET' }
            }).show(param);
        },
        update: function(journal, file) {

            var fdata = getFormData(journal, file);
            var urlOverload = file == undefined ? '/nofile' : '/file';

            return $resource(journalUrl + urlOverload, { }, {
                postWithFile: {
                    method: "POST",
                    params: fdata,
                    transformRequest: angular.identity,
                    headers: { 'Content-Type': undefined }
                }
            }).postWithFile(fdata).$promise;
        },
        delete: function(param) {
            return $resource(journalUrl + '/:id', {}, {
                delete: { method: 'DELETE', params: {id: '@id'} }
            }).delete(param).$promise;
        }

    }});