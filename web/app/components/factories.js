'use strict';

/**
 * FlightFactory
 * 
 * @param angular http
 * @returns Object containing flight factory methods
 */
angular.module('myApp').factory('FlightFactoty', ["$http", function (http) {

        var flight = {};

        flight.searchWithNoDestination = function (from, time, seats) {
            var url = "api/search/" + from + "/" + time + "/" + seats;

            return http.get(url);
        };

        flight.searchWithDestination = function (from, to, time, seats) {
            var url = "api/search/" + from + "/" + to + "/" + time + "/" + seats;

            return http.get(url);
        };

        return flight;
    }]);

angular.module('myApp').factory('ReservationFactoty', ["$http", function (http) {

        var reservation = {};

        reservation.reservateTickets = function (ticketRequest) {
            var url = "api/flightreservation";
            return http.post(url, ticketRequest);
        };

        reservation.reservateExternalTickets = function (ticketRequest) {
            var url = "api/flightreservation/external";
            return http.post(url, ticketRequest);
        };

        return reservation;
    }]);

/**
 * Airport Factory.
 * 
 * @Author: Casper Schultz
 * @Date: 5/12 2015
 * 
 * @param angular $http 
 * @returns Object containing methods for alking with the Airport API
 */
angular.module('myApp').factory('AirportFactoty', ["$http", "$q", function (http, $q) {
        
        var baseUrl = "api/airport/";
        var airport = {};

        airport.getAirportsByCity = function (name) {
            var url = "api/airport/city/" + name;
            return http.get(url);
        }

        airport.getAirportByIATA = function (iata) {
            var url = "api/airport/" + iata;
            return http.get(url);
        }

        airport.isValidAirport = function (city) {
            var url = "api/airport/valid/" + city;
            return http.get(url);
        }
        
        airport.getAirportNames = function(str) {
            
            // We wont call the server, if the string is less then 3 long
            // But we return an empty promise instead
            if (str.length < 3) {
                return $q.when("");
            }
            
            /**
             * The then function returns a promise by itself, so
             * we can actually handle data in here, and then return the promise.
             */
            var promise = http.get("api/airport/city/" + str).then(function(response) {
                
                // We run through the list, and get the info needed
                var airports = [];
                
                for (var i = 0, l = response.data.length; i < l; i++) {
                    airports.push(response.data[i].country + ", " + response.data[i].city + ", " + response.data[i].name);
                }
                 
                return airports;
            });
            
            return promise;
        }   

        return airport;
    }]);

/**
 * Stores flightdata temporary.
 * 
 * @returns {data}
 */
angular.module('myApp').factory('FlightSaver', function () {
    
    var flightSaver = {};
    
    var savedData;

    flightSaver.set = function (data) {
        savedData = data;
    };

    flightSaver.get = function () {
        return savedData;
    };


    return flightSaver;
});

app.factory('SignupFactory',[ '$http', function (http) {

    var signup = this;

    signup.signup = function (user) {
        var url = "api/register";
        return http.post(url, user);
    }

    return signup;
}]);

