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
                    
                search.searchWithNoDestination = function(from, time, seats) {
                    var url = "api/search/" + from + "/" + time + "/" + seats;

                    return http.get(url).then(function (result) {
                        return result.data;
                    });
                };

                search.searchWithDestination = function(from, to, time, seats) {
                    var url = "api/search/" + from + "/" + to + "/" + time + "/" + seats;

                    return http.get(url);
                };


                return search;
            }]);