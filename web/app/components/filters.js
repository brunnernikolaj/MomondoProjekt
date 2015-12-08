'use strict';
    
    app.filter('traveltime', function() {
        return function(input, traveltime) {
            var h = Math.floor(input / 60); // Math.floo() runder ned
            var m = (input - (h*60)); // træk timer fra minuttal

            var time = h + 't ' + m + 'm';
            return time;
        };
    });
