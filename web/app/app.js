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
  'toastr',
  'rzModule',
  'ui.bootstrap',
  'myApp.security',
  'myApp.view2',
  'myApp.view3',
  'myApp.directives'
]);

/**
 * Route configuration for the app
 * 
 * @param {type} $routeProvider
 */
app.config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
    
    /**
     * We add auth headers on every http request. See the authentication
     * module for more info.
     */
    $httpProvider.interceptors.push('AuthInterceptor');
        
    $routeProvider
    .when('/login', {
      templateUrl: 'app/templates/loginForm.html',
      controller: 'AppCtrl',
      controllerAs : 'ctrl'
    })
    .when('/signup', {
      templateUrl: 'app/templates/signupForm.html',
      controller: 'SignupCtrl'
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
