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
  directive('flightResult', function () {
    return {
      restrict: 'E',
      scope: {
        flight: '=result'
      },
      templateUrl: 'app/partials/flightResult.html',
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
