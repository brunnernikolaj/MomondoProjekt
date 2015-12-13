'use strict';

/**
 * Shows a flightresult 
 * 
 * Requires a flight object named flight to be defined
 * 
 * @param {type} param1
 * @param {type} param2
 */
angular.module('myApp').
  directive('flightResult', function ($templateCache) {
      
    return {
      restrict: 'E',
      scope: {
        flight: '=result',
        reserve: '=reserve'
      },
      template: $templateCache.get('flightResult.html'),
      link: function(scope, element, attr) {
          
          
          element.on('mouseover', function(event) {
              element.css({
                opacity: '0.8'
               });
          });
          
          element.on('mouseleave', function(event) {
              element.css({
                opacity: '1'
               });
          });
      }
    };
  });

angular.module('myApp').
  directive('noResult', function () {
    return {
      templateUrl: 'app/partials/noResult.html'
    };
  });
