'use strict';

/**
 * Main app module and the dependencies 
 * required.
 * 
 * @param {type} param
 */
var app = angular.module('myApp', [
  'ngRoute',
  'ngAnimate',
  'autocomplete',
  'rzModule',
  'ui.bootstrap',
  'myApp.security',
  'myApp.view2',
  'myApp.view3',
  'myApp.filters',
  'myApp.directives',
  'myApp.factories',
  'myApp.services',
  'toastr'
]);

/**
 * Route configuration for the app
 * 
 * @param {type} $routeProvider
 */
app.config(['$routeProvider', function($routeProvider) {
        
    $routeProvider
    .when('/login', {
      templateUrl: 'app/templates/loginForm.html',
      controller: 'AppCtrl',
      controllerAs : 'ctrl'
    })
    .when('/signup', {
      templateUrl: 'app/templates/signupForm.html'
    })
    .when('/doc', {
      templateUrl: 'aap/templates/documentation.html'
    })
    .when('/searchresult', {
      templateUrl: 'app/templates/searchresult.html',
      controller: 'SearchCtrl',
    })
    .when('/booking', {
      templateUrl: 'app/templates/booking.html',
      controller: 'BookingCtrl'
    })
    .when('/', {
      templateUrl: 'app/templates/searchresult.html',
      controller: 'SearchCtrl',
    })
    // Make frontpage after Sprint 1, is over.
    // .when('/', {
    //     templateUrl: 'app/templates/frontpage.html',
    //     controller: 'FrontpageCtrl',
    //     controllerAs : 'forntpageCtrl'
    // })
    .otherwise({redirectTo: '/'});
}]);

/**
 * We add the filter for adding the authentication
 * header tag along with the http requests.
 * 
 * @param {type} $httpProvider
 */
app.config(function ($httpProvider) {
   $httpProvider.interceptors.push('AuthInterceptor');
});


