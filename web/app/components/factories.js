'use strict';

/**
 * FlightFactory
 * 
 * @param angular http
 * @returns Object containing flight factory methods
 */
app.factory('FlightFactoty', ["$http", function (http) {

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

app.factory('ReservationFactoty', ["$http", function (http) {

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
app.factory('AirportFactoty', ["$http", function (http) {

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

        return airport;
    }]);

/**
 * Stores flightdata temporary.
 * 
 * @returns {data}
 */
app.factory('flightSaver', function () {

    var savedData = {}

    savedData.set = function (data) {
        savedData = data;
    }

    savedData.get = function () {
        return savedData;
    }

    return savedData;
});

app.factory('SignupFactory',[ '$http', function (http) {

    var signup = this;

    signup.signup = function (user) {
        var url = "api/register";
        return http.post(url, user);
    }

    return signup;
}]);