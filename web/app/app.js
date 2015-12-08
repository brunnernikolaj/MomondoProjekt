'use strict';

/**
 * Here we init the module and all its dependencies. 
 * 
 * To read more about the concept we are using to structure our app,
 * read here: http://briantford.com/blog/huuuuuge-angular-apps
 * 
 * @param {type} param
 */
angular.module('myApp', [
  'ngRoute',
  'ngAnimate',
  'autocomplete',
  'toastr',
  'rzModule',
  'ui.bootstrap',
  'security',
  'myApp.view2',
  'myApp.view3',
]);

/**
 * Route configuration for the app
 * 
 * @param {type} $routeProvider
 */
angular.module('myApp').config(['$routeProvider', '$httpProvider','toastrConfig', function($routeProvider, $httpProvider, toastrConfig) {
    
    /**
     * We add auth headers on every http request. See the authentication
     * module for more info.
     */
    $httpProvider.interceptors.push('AuthInterceptor');
        
    $routeProvider
    .when('/login', {
      templateUrl: 'app/templates/loginForm.html',
      controller: 'LoginCtrl',
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
      controller: 'SearchCtrl'
    })
    .when('/booking', {
      templateUrl: 'app/templates/booking.html',
      controller: 'BookingCtrl',
      auth: 'User'
    })
     .when('/my-reservations', {
      templateUrl: 'app/templates/my-reservations.html',
      controller: 'MyReservationsCtrl',
      auth: 'Admin'
    })
    .when('/', {
      templateUrl: 'app/templates/searchresult.html',
      controller: 'SearchCtrl'
    })
    // Make frontpage after Sprint 1, is over.
    // .when('/', {
    //     templateUrl: 'app/templates/frontpage.html',
    //     controller: 'FrontpageCtrl',
    //     controllerAs : 'forntpageCtrl'
    // })
    .otherwise({redirectTo: '/'});
    
    angular.extend(toastrConfig, {
    positionClass: 'toast-bottom-left',
    preventDuplicates: false,
    preventOpenDuplicates: false,
    target: 'body'
  });

}]);
