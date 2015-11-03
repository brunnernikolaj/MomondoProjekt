'use strict';

angular.module('myApp.frontpage', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/', {
    templateUrl: 'app/frontpage/frontpage.html',
    controller: 'FrontpageCtrl',
    controllerAs : 'ctrl'
  });
}])

.controller('FrontpageCtrl', ["InfoFactory","InfoService",function(InfoFactory,InfoService) {
  this.msgFromFactory = InfoFactory.getInfo();
  this.msgFromService = InfoService.getInfo();
}]);