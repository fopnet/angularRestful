/**
 * Created by Felipe on 09/07/2016.
 */
(function () {
    'use strict';

    services.factory('FlashService', FlashService);

    FlashService.$inject = ['$rootScope'];
    function FlashService($rootScope) {
        var service = {};

        service.Success = Success;
        service.Error = Error;

        initService();

        return service;

        function initService() {
            $rootScope.$on('$locationChangeStart', function () {
                clearFlashMessage();
            });

            function clearFlashMessage() {
                var flash = $rootScope.flash;
                if (flash) {
                    if (!flash.keepAfterLocationChange) {
                        delete $rootScope.flash;
                    } else {
                        // only keep for a single location change
                        flash.keepAfterLocationChange = false;
                    }
                }
            }
        }

        function Success(message, keepAfterLocationChange) {
            $rootScope.flash = {
                message: message,
                type: 'success',
                keepAfterLocationChange: keepAfterLocationChange
            };
        }

        function Error(response, keepAfterLocationChange) {
            var message = 'ngdemo: XHR status:' + response.status + ' erro:' + response.data;

            if (response.config != null)
                message += 'ao chamar ' + response.config.url + ' , please try again';
            else
                message += response.message;

            $rootScope.flash = {
                message: message,
                type: 'error',
                keepAfterLocationChange: keepAfterLocationChange
            };
        }
    }

})();