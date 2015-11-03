'use strict';

/**
 * Main app module and the dependencies 
 * required.
 * 
 * @param {type} param
 */
angular.module('myApp', [
  'ngRoute',
  'ngAnimate',
  'ui.bootstrap',
  'myApp.security',
  'myApp.view2',
  'myApp.view3',
  'myApp.filters',
  'myApp.directives',
  'myApp.factories',
  'myApp.services',
  'myApp.controllers'
])

/**
 * Route configuration for the app
 * 
 * @param {type} $routeProvider
 */
.config(['$routeProvider', function($routeProvider) {
        
    $routeProvider
    .when('/login', {
      templateUrl: 'app/templates/loginForm.html'
    })
    .when('/', {
        templateUrl: 'app/templates/frontpage.html',
        controller: 'FrontpageCtrl',
        controllerAs : 'forntpageCtrl'
    })
    .otherwise({redirectTo: '/'});
}])

/**
 * We add the filter for adding the authentication
 * header tag along with the http requests.
 * 
 * @param {type} $httpProvider
 */
.config(function ($httpProvider) {
   $httpProvider.interceptors.push('AuthInterceptor');
});


