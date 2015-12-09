'use strict';

/**
 * FlightFactory
 * 
 * @param angular http
 * @returns Object containing flight factory methods
 */
angular.module('myApp').factory('FlightFactory', ["$http", function (http) {

        var flight = {};
        
        flight.searchForFlights = function(from, to, time, seats) {
            
            //if (from === undefined || from === "" || time === undefined || time === "" || seats === undefined || seats === "") {
                // Not really sure yet.
            //}
            
            // Format the date
            var date = new Date(time).toISOString();
            
            console.log(from + " - " + to + " - " + date + " - " + seats)
            
            if (to) {
                return searchWithDestination(from, to, date, seats).then(function(res) {
                    return res.data;
                });
            } else {
                return searchWithNoDestination(from, date, seats).then(function(res) {
                    return res.data;
                });
            }
        }
        
        function searchWithNoDestination(from, time, seats) {
            var url = "api/search/" + from + "/" + time + "/" + seats;

            return http.get(url);
        };

        function searchWithDestination(from, to, time, seats) {
            var url = "api/search/" + from + "/" + to + "/" + time + "/" + seats;

            return http.get(url);
        };
        
        

        return flight;
    }]);

angular.module('myApp').factory('ReservationFactory', ["$http", function (http) {

        var reservation = {};

        reservation.reservateTickets = function (ticketRequest) {
            var url = "api/flightreservation";
            return http.post(url, ticketRequest);
        };

        reservation.reservateExternalTickets = function (ticketRequest) {
            var url = "api/reservation";
            return http.post(url, ticketRequest);
        };
        
        reservation.getAll = function () {
            var url = "api/reservation";
            return http.get(url);
        };
        
        reservation.getByUser = function (username) {
            var url = "api/reservation/user/" + username;
            return http.get(url);
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
 * @param angular $q 
 * @returns Object containing methods for alking with the Airport API
 */
angular.module('myApp').factory('AirportFactory', ["$http", "$q", function (http, $q) {
        
        var airports = [];
        
        var airport = {};
        
        airport.getAirports = function() {
            return airports;
        }
        
        airport.getAirportsByCity = function (name) {
            var url = "api/airport/city/" + name;
            return http.get(url);
        };

        airport.getAirportByIATA = function (iata) {
            var url = "api/airport/" + iata;
            return http.get(url);
        };

        airport.isValidAirport = function (city) {
            var url = "api/airport/valid/" + city;
            return http.get(url);
        };
        
        /**
         * Get local stored airport by name.
         * 
         * @param {type} name
         * @returns {unresolved}
         */
        airport.getLocalStoredAirportByName = function(name) {
            
            for (var i = 0, l = airports.length; i < l; i++) {
                if (airports[i].name.toLowerCase() == name.toLowerCase()) {
                    return airports[i];
                }
            }
            
            throw "An error occured calling getAirportByName. Trying to get airport name when no local airports are stored";
        }
        
        /**
         * Get airports from string
         * 
         * @param {type} str
         * @returns {unresolved}
         */
        airport.getAirportNiceNames = function(str) {
            
            // We wont call the server, if the string is less then 3 long
            // But we return an empty promise instead
            if (str.length < 3) {
                throw "getAirportNames expects an input of a string that is atleast 3 characters long";
            }
            
            var airportNames = [];
            
            // We fetch the list when a character list contains of 3 letters
            // so its safe to look up locally after.
            if (str.length > 3 && airports.length > 0) {
                
                for (var i = 0, l = airports.length; i < l; i++) {
                    if (str == airports[i].city.substring(0, str.length).toLowerCase()) {
                        airportNames.push(airports[i].country + ", " + airports[i].city + ", " + airports[i].name);
                    }
                }
                 
                return $q.when(airportNames);
            }
           
            
            return airport.getAirportsByCity(str).then(function(response) {
                
                airports = response.data;
                
                for (var i = 0, l = response.data.length; i < l; i++) {
                    airportNames.push(response.data[i].country + ", " + response.data[i].city + ", " + response.data[i].name);
                }
                 
                return airportNames;
            });
        };   

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

