/*
 * Angular authentication module
 * 
 * A rewrite of the security module provided for the semester projects.
 * 
 * @Author: Casper Schultz
 */
'use strict';

angular.module('security', [])

        /**
         * URL base decoder
         * 
         * @returns {Function}
         */
        .service('UrlBaseDecode', function () {

            return function (str) {

                var output = str.replace('-', '+').replace('_', '/');

                switch (output.length % 4) {
                    case 0:
                        break;
                    case 2:
                        output += '==';
                        break;
                    case 3:
                        output += '=';
                        break;
                    default:
                        throw 'Illegal base64url string!';
                }

                return window.atob(output); //polifyll https://github.com/davidchambers/Base64.js
            };
        })


        /**
         * Factory adds authentication header
         * 
         * @param {type} $rootScope
         * @param {type} $q
         * @param {type} $window
         * @returns {authentication_L41.authenticationAnonym$1}
         */
        .factory('AuthInterceptor', function ($rootScope, $q, $window) {
            return {
                request: function (config) {
                    config.headers = config.headers || {};
                    if ($window.sessionStorage.token) {
                        config.headers.Authorization = 'Bearer ' + $window.sessionStorage.token;
                    }
                    $rootScope.error = "";
                    return config;
                },
                responseError: function (rejection) {
                    return $q.reject(rejection);
                }
            };
        })


        /**
         * Factory for handling the login / logout events.
         * 
         * @param {type} $http
         * @param {type} $window
         * @param {type} $location
         * @param {type} UrlBaseDecode
         * @returns {authentication_L36.auth}
         */
        .factory('LoginFactory', function ($http, $window, $rootScope, UrlBaseDecode) {

            var auth = {
                username: "",
                authenticated: false,
                roles: [],
                admin: false,
                user: false,
                message: '',
                error: null
            };

            //This sets the login data from session store if user pressed F5 (You are still logged in)
            var init = function () {
                var token = $window.sessionStorage.token;
                if (token) {
                    auth.authenticated = true;
                    var encodedProfile = token.split('.')[1];
                    var profile = JSON.parse(UrlBaseDecode(encodedProfile));
                    auth.username = profile.username;

                    var roles = profile.roles.split(",");
                    roles.forEach(function (role) {
                        auth.roles.push(role);
                    });
                    auth.error = null;
                }
            };

            // and fire it after definition
            init();

            auth.isLoggedIn = function () {
                return this.authenticated;
            };

            auth.isAdmin = function () {
                return this.roles.indexOf('Admin') !== -1;
            };

            auth.isUser = function () {
                return this.roles.indexOf('User') !== -1;
            };
            
            auth.isRole = function (role) {
                return this.roles.indexOf(role) !== -1;
            };

            auth.getUsername = function () {
                return this.username;
            };

            auth.doLogout = function () {
                this.authenticated = false;
                this.roles = [];
                delete $window.sessionStorage.token;
                $rootScope.$broadcast('auth:loggedOut', auth);
            };

            auth.doLogin = function (userJson) {

                var self = this;

                $http.post('api/login', userJson).success(function (data) {

                    $window.sessionStorage.token = data.token;
                    self.authenticated = true;

                    var encodedProfile = data.token.split('.')[1];
                    var profile = JSON.parse(UrlBaseDecode(encodedProfile));
                    self.username = profile.username;

                    self.roles = [];
                    var roles = profile.roles.split(",");
                    roles.forEach(function (role) {
                        auth.roles.push(role);
                    });

                    auth.error = null;
                    $rootScope.$broadcast('auth:loggedIn', auth);
                })
                        .error(function (data) {
                            $rootScope.$broadcast('auth:failedLogin', "Brugernavn og password stemmer ikke overens");
                            delete $window.sessionStorage.token;
                            self.authenticated = false;
                            self.roles = [];
                            self.username = "";
                            self.error = data.error;
                        });
            };

            return auth;
        });


/**
 * NOT SURE ABOUT WHY THESE HAS BEEN ADDED IN THE FIRST PLACE
 * 
 * They are not needed
 * 
 * So for now, I will comment them out, and find a solution, when/if
 * I will ever need it.
 */
//  //Other controller emits the logOutEvent to force a logout
//  $scope.$on('logOutEvent', function (event, args) {
//    $scope.error = "Your session timed out. Please login again";
//    $scope.logout();
//  });
//
//  $scope.isActive = function (viewLocation) {
//    return viewLocation === $location.path();
//  };
