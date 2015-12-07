'use strict';

/**
 * Flight website angular factories.
 * 
 * @param Modulename
 * @param Module dependencies
 */
angular.module('myApp.factories', [])

        /**
         * FlightFactory
         * 
         * @param angular http
         * @returns Object containing flight factory methods
         */
        .factory('FlightFactoty', ["$http", function (http) {

                var flight = {};

                flight.searchWithNoDestination = function (from, time, seats) {
                    var url = "api/search/" + from + "/" + time + "/" + seats;

                    return http.get(url);
                };

                flight.searchWithDestination = function (from, to, time, seats) {
                    var url = "api/search/" + from + "/" + to + "/" + time + "/" + seats;

                    return http.get(url);
                };

                flight.reservateTickets = function (ticketRequest) {
                    var url = "api/flightreservation";
                    return http.post(url, ticketRequest);
                };

                flight.reservateExternalTickets = function (ticketRequest) {
                    var url = "api/flightreservation/external";
                    return http.post(url, ticketRequest);
                };

                return flight;
            }])

        .factory('ReservationFactoty', ["$http", function (http) {
                this.reservateTickets = function (ticketRequest) {
                    var url = "api/flightreservation";
                    return http.post(url, ticketRequest);
                };

                this.reservateExternalTickets = function (ticketRequest) {
                    var url = "api/flightreservation/external";
                    return http.post(url, ticketRequest);
                };

                return this;
            }])

        /**
         * Airport Factory.
         * 
         * @Author: Casper Schultz
         * @Date: 5/12 2015
         * 
         * @param angular $http 
         * @returns Object containing methods for alking with the Airport API
         */
        .factory('AirportFactoty', ["$http", function (http) {

                var airport = {};

                /**
                 * 
                 * @param String name       The name of the aiprot to lookup
                 * @returns                 Promise / airport objects.
                 */
                airport.getAirportsByName = function (name) {
                    var url = "api/airport/city/" + name;
                    return http.get(url);
                }

                return airport;
            }])
        
        /**
         * Stores flightdata temporary.
         * 
         * @returns {data}
         */
        .factory('flightSaver', function () {
            
            var savedData = {}
            
            savedData.set = function(data) {
                savedData = data;
            }
            
            savedData.get = function() {
                return savedData;
            }

            return savedData;
        });