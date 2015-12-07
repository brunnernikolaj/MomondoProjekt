'use strict';

/* Place your global Factory-service in this file */

angular.module('myApp.factories', [])

        .factory('InfoFactory', function () {
            var info = "Hello World from a Factory";
            var getInfo = function getInfo() {
                return info;
            };
            return {
                getInfo: getInfo
            };
        })


        .factory('FlightFactoty', ["$http", function (http) {

                var search = {};

                search.searchWithNoDestination = function (from, time, seats) {
                    var url = "api/search/" + from + "/" + time + "/" + seats;

                    return http.get(url);
                };

                search.searchWithDestination = function (from, to, time, seats) {
                    var url = "api/search/" + from + "/" + to + "/" + time + "/" + seats;

                    return http.get(url);
                };


                return search;
            }])

            .factory('ReservationFactoty', ["$http", function (http) {
                this.reservateTickets = function (ticketRequest) {
                    var url = "api/flightreservation";

                    return http.post(url,ticketRequest);
                };
                
                this.reservateExternalTickets = function (ticketRequest) {
                    var url = "api/flightreservation/external";

                    return http.post(url,ticketRequest);
                };

                return this;
            }])
        
        .factory('AirportFactoty', ["$http", function (http) {

                var airport = {
                    airports: undefined
                };
                    
                airport.getAirportsByName = function(name) {
                    var url = "api/airport/city/" + name;
                    return http.get(url);
                }

                return airport;
            }])

            .factory('ReservationFactoty', ["$http", function (http) {
                this.reservateTickets = function (ticketRequest) {
                    var url = "api/flightreservation/";

                    return http.post(url,ticketRequest);
                };

                return this;
            }])
            
        .factory('flightSaver', function () {
            var savedData = {}
            function set(data) {
                savedData = data;
            }
            function get() {
                return savedData;
            }

            return {
                set: set,
                get: get
            }

        });