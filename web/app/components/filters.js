'use strict';

/* Place your Global Filters in this file */

angular.module('myApp.filters', [])
	.filter('checkmark', function () {
        return function(input) {
          return input ? '\u2713' : '\u2718';
        };
    })
    
    .filter('traveltime', function() {
        return function(input, traveltime) {
            var h = Math.floor(input / 60); // Math.floo() runder ned
            var m = (input - (h*60)); // træk timer fra minuttal

            var time = h + 't ' + m + 'm';
            return time;
        };
    });
