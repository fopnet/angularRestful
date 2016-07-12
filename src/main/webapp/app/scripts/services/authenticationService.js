/**
 * Created by Felipe on 09/07/2016.
 */
(function () {
    'use strict';

    var authUrl = baseUrl + '/web/users';

    services.factory('AuthenticationService', AuthenticationService);

    AuthenticationService.$inject = ['$http', '$base64', '$cookieStore', '$rootScope', '$timeout', 'UserFactory'];
    function AuthenticationService($http, $base64, $cookieStore, $rootScope, $timeout, UserFactory) {
        var service = {};

        service.Login = Login;
//        service.SetCredentials = SetCredentials;
        service.ClearCredentials = ClearCredentials;
        service.isPublic = isPublic;
        service.isPublisher = isPublisher;
        service.Logout = Logout;

        return service;

        function Login(email, password, callback, errorCalback) {

            /* Use this for real authentication
             ----------------------------------------------*/
     /*       $http({
                url:authUrl + '/authenticate',
                method: 'POST',
                params: {
                    username: email,
                    password: password
                }
            }).then (callback , errorCalback);*/

            var data = "username=" + email + "&password=" + password;
//            var data = {'username': email, 'password':  password};
            var config = {
                headers : {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;',
                     authorization : "Basic " + btoa(email + ":" + password)
                }
            };

            // {"username": email, "password": password}
            $http.post(authUrl + '/authenticate', data, config)
                        //.then (callback , errorCalback)
                        .then(function (response) {
                            SetCredentials(response.data, response.config.headers["X-XSRF-TOKEN"]);

                            return callback(response);
                        }, errorCalback);
        }

        function SetCredentials(credentials, csrfToken) {
//            var authdata = $base64.encode(username + ':' + password);
//            var authdata = btoa(username + ':' + password);

            $rootScope.authenticated = true;

            $rootScope.globals = {
                currentUser: {
                    username: credentials.username,
                    authdata: credentials.authdata,
                    roles: credentials.roles,
                    csrf: csrfToken
                }
            };

//            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $http.defaults.headers.common['Authorization'] = credentials.authdata;
            $http.defaults.headers.common['X-XSRF-TOKEN'] = csrfToken;
            $cookieStore.put('globals', $rootScope.globals);
        }

        function isPublisher() {
            return isAuthenticated() && hasRole('ROLE_PUBLISHER');
        }

        function isPublic() {
            return isAuthenticated() && hasRole('ROLE_PUBLIC');
        }

        function hasRole(rolename) {
            return $rootScope.globals.roles && $rootScope.globals.roles.indexOf(rolename) > -1;
        }

        function isAuthenticated() {
            return $rootScope.authenticated;
        }

        function ClearCredentials() {
            $rootScope.globals = {};
            $rootScope.authenticated = false;
            $cookieStore.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic';
            $http.defaults.headers.common['X-XSRF-TOKEN'] = '';
        }

        function Logout() {
            return $http.get(authUrl + '/logout', {})
            .finally(function() {
                ClearCredentials();
            });
        }
    };
})();
