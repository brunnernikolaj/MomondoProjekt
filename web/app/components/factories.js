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


        .factory('SearchFactory', ["$http", function (http) {

            var search = function search(from,time,seats) {
                var url = "api/search/" + from + "/" + time + "/" + seats; 
                
                return http.get(url).then(function (result) {
                    return result.data;
                });
            };
            
            
            return {
                search: search
            };
        }]);