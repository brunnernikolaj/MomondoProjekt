'use strict';

/**
 * FlightFactory
 * 
 * @param angular http
 * @returns Object containing flight factory methods
 */
angular.module('myApp').factory('FlightFactory', ["$http", 'AirportFactory', '$q', function (http, AirportFactory, $q) {
        
        var lastSearch = {
            result: null,
            from: null,
            to: undefined,
            time: null,
            seats: 0,
            max: 0
        };
        
        var flight = {};
        
        flight.getLastSearch = function() {
            
            if (lastSearch.result != null) {
                return lastSearch;
            }
            
            // We provide support for localstorage, if supported
            if (localStorage.lastSearch != undefined) {
                var obj = localStorage.lastSearch;
                var res = JSON.parse(obj);
                return res;
            }
            
            throw = "FlightFactory: The object lastSearch has not been set, so there is no value to retrieve";
        }
        
        flight.unpackFlights = function(result) {
            
            var maxValue = 0;
            
            result.forEach(function (airline, index) {

                airline.flights.forEach(function (flight, index) {
                    flight.airline = airline.airline;
                    var date = new Date(flight.date);
                    flight.endDate = new Date(date.setMinutes(date.getMinutes() + flight.traveltime)).toISOString();

                    if (flight.totalPrice > maxValue) {
                        maxValue = flight.totalPrice;
                    }
                });
            })

            //Select all flight arrays and then flatten them to one array
            var flights = result.map(airline => airline.flights);
            var flattened = [];
            for (var i = 0; i < flights.length; ++i) {
                var current = flights[i];
                for (var j = 0; j < current.length; ++j)
                    flattened.push(current[j]);
            }
            
            lastSearch.result = flattened;
            lastSearch.max = maxValue;
            
            // We store the result in local storage aswell, so it's still there if
            // the user updates his browser window
            if(typeof(Storage) !== "undefined") {
                var obj = JSON.stringify(lastSearch);
                localStorage.setItem("lastSearch", obj);
            } 
            
            return $q.when({
                arr: flattened,
                max: maxValue
            });
        }
        
        
        flight.searchForFlights = function(from, time, seats, to) {
            
            if (arguments.length < 3) {
                throw "An error occured while calling searchForFlights. one of the required arguments is undefined";
            }
            
            var date = new Date(time).toISOString();
            
            lastSearch.time = date;
            lastSearch.from = from;
            lastSearch.to = to;
            lastSearch.seats = seats;
            
            if (to) {
                return searchWithDestination(from, date, seats, to).then(function(res) {
                    return res.data;
                });
            } else {
                return searchWithNoDestination(from, date, seats).then(function(res) {
                    return res.data;
                });
            }
        }
        
        
        flight.attachAirportNames = function(flights) {
            
            // Airports we should fetch
            var airportCodes = [];

            // First we gotta loop through and get all the different iata codes
            // and prepare the flight object for the new data.
            for (var i = 0, l = flights.length; i < l; i++) {

                // Prepare for populating later
                flights[i].originCity = "";
                flights[i].originName = "";
                flights[i].destinationCity = "";
                flights[i].destinationName = "";

                if (airportCodes.indexOf(flights[i].origin) == -1) {
                    airportCodes.push(flights[i].origin);
                }

                if (airportCodes.indexOf(flights[i].destination) == -1) {
                    airportCodes.push(flights[i].destination);
                }
            }

            // Now we fetch the airport names.
            for (var c = 0, d = airportCodes.length; c < d; c++) {
                AirportFactory.getAirportByIATA(airportCodes[c]).then(function (res) {

                    for (var j = 0; j < flights.length; j++) {
                        if (flights[j].origin == res.data.IATAcode) {
                            flights[j].originName = res.data.name;
                            flights[j].originCity = res.data.city;
                        }

                        if (flights[j].destination == res.data.IATAcode) {
                            flights[j].destinationName = res.data.name;
                            flights[j].destinationCity = res.data.city;
                        }
                    }
                });
            }

            return flights;
        }
        
        function searchWithNoDestination(from, time, seats) {
            var url = "api/search/" + from + "/" + time + "/" + seats;
            return http.get(url);
        };

        function searchWithDestination(from, time, seats, to) {
           
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
    
    var savedData = undefined;

    flightSaver.set = function (data) {
        
        savedData = data;
        
        if(typeof(Storage) !== "undefined") {
            var obj = JSON.stringify(savedData);
            localStorage.setItem("savedData", obj);
        } 
    };

    flightSaver.get = function () {
        
        if (savedData != undefined) {
            return savedData;
        }
        
        if (localStorage.lastSearch != undefined) {
            var obj = localStorage.savedData;
            var res = JSON.parse(obj);
            return res;
        }
        
        throw "No data has been saved in FlightSaver, so it is not possible to retrieve any.";
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

