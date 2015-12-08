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
    .service('UrlBaseDecode', function() {
            
        return function(str) {
            
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
    .factory('LoginFactory', function($http, $window, $rootScope, UrlBaseDecode, toastr) {
        
        var auth = {
            username: "",
            authenticated: false,
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
            auth.admin = profile.role === "Admin";
            auth.user = !auth.admin;
            auth.error = null;
          }
        };
        
        // and fire it after definition
        init();
        
        auth.isLoggedIn = function() {
            return this.authenticated;
        };
        
        auth.isAdmin = function() {
            return this.admin;
        };
        
        auth.isUser = function() {
            return this.user;
        };
        
        auth.getUsername = function() {
          return this.username;  
        };
        
        auth.doLogout = function() {
            this.authenticated = false;
            this.admin = false;
            this.user = false;
            toastr.success('', 'Du er nu logget ud');
            delete $window.sessionStorage.token;
            $rootScope.$broadcast('auth:loggedOut', auth);
        };
         
        auth.doLogin = function(userJson) {
            
            var self = this;
            
            $http.post('api/login', userJson).success(function (data) {
                
                $window.sessionStorage.token = data.token;
                self.authenticated = true;
                
                var encodedProfile = data.token.split('.')[1];
                var profile = JSON.parse(UrlBaseDecode(encodedProfile));
                self.username = profile.username;
                toastr.success('', 'Du er nu logget ind');
                
                var roles = profile.roles.split(",");
                roles.forEach(function (role) {
                    if(role === "Admin") {
                        self.admin =true;
                    }
                    if(role === "User") {
                        self.user = true;
                    }
                });
              
                auth.error = null;
                $rootScope.$broadcast('auth:loggedIn', auth);
            })
            .error(function (data) {
              toastr.error('Login mislykkedes');
              $rootScope.$broadcast('auth:failedLogin', "Brugernavn og password stemmer ikke overens");
              delete $window.sessionStorage.token;
              self.authenticated = false;
              self.admin = false;
              self.user = false;
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
