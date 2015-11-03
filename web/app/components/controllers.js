/*
 * Place your global Controllers in this file
 */

angular.module('myApp.controllers', ['myApp.security'])
  
/**
 * Main page ctrl
 */
.controller('AppCtrl', function ($scope, $location, LoginFactory) {
    
    var self = this;
    
    self.title = "My better seed";
    self.project = "InfoApp";
    
    self.authenticated = LoginFactory.isLoggedIn();
    self.username = LoginFactory.getUsername();
    
    $scope.$on('auth:loggedIn', function(event, args) {
        self.authenticated = args.isLoggedIn();
        self.username = args.getUsername();
        $location.path('/');
    });
    
    $scope.$on('auth:loggedOut', function(event, args) {
        self.authenticated = args.isLoggedIn();
        self.username = args.getUsername();
        $location.path('/');
    });
    
    self.doLogin = function() {
        LoginFactory.doLogin(self.user);
        self.user = {};
    };
    
    self.doLogout = function() {
        LoginFactory.doLogout();
    };
})

.controller('FrontpageCtrl', ["InfoFactory","InfoService",function(InfoFactory,InfoService) {
  this.msgFromFactory = InfoFactory.getInfo();
  this.msgFromService = InfoService.getInfo();
}]);



