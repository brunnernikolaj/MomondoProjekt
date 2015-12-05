/*
 * Place your global Controllers in this file
 */

angular.module('myApp.controllers', ['myApp.security'])

        /**
         * Main page ctrl
         */
        .controller('AppCtrl', function ($scope, $location, LoginFactory) {

            var self = this;

            self.title = "JustFly";
            self.project = "JustFly";

            self.authenticated = LoginFactory.isLoggedIn();
            self.username = LoginFactory.getUsername();

            $scope.$on('auth:loggedIn', function (event, args) {
                self.authenticated = args.isLoggedIn();
                self.username = args.getUsername();
                $location.path('/');
            });

            $scope.$on('auth:loggedOut', function (event, args) {
                self.authenticated = args.isLoggedIn();
                self.username = args.getUsername();
                $location.path('/');
            });

            self.doLogin = function () {
                LoginFactory.doLogin(self.user);
                self.user = {};
            };

            self.doLogout = function () {
                LoginFactory.doLogout();
            };
        })

        .controller('FrontpageCtrl', ["FlightFactoty", function (searchService) {

                var self = this;

                self.flights = [];

                self.click = function () {

                    searchService.search("CPH", "2016-02-01T23:00:00.000Z", 1).then(function (result) {
                        var flights = result.map(airline => airline.flights);

                        var flattened = [];
                        for (var i = 0; i < flights.length; ++i) {
                            var current = flights[i];
                            for (var j = 0; j < current.length; ++j)
                                flattened.push(current[j]);
                        }

                        self.flights = flattened;
                    });
                }
            }])

        /**
         * Search form controller.
         * 
         * @Author: Casper Schultz
         * @Date: 4/12 2015
         * 
         * @returns {undefined}
         */
        .controller("SearchCtrl", ['$scope', 'FlightFactoty', function ($scope, FlightFactoty) {
                $scope.cities = ["CPH","SXF"];

                // gives another movie array on change
        $scope.updateCities = function(typed){
            // MovieRetriever could be some service returning a promise
            $scope.cities = ["CPH","SXF"];
      
        }

                // handle incomming data
                $scope.searchFlights = function () {
                    var searchQuery = $scope.search;

                    var date = new Date(searchQuery.date).toISOString();

                    FlightFactoty.searchWithDestination(searchQuery.from, searchQuery.to, date, searchQuery.seats).then(function (result) {


                        if (result.data[0] != null) {
                            var flights = result.data.map(airline => airline.flights);
                            var flattened = [];
                            for (var i = 0; i < flights.length; ++i) {
                                var current = flights[i];
                                for (var j = 0; j < current.length; ++j)
                                    flattened.push(current[j]);
                            }

                            flattened.forEach(function (element, index) {
                                var date = new Date(element.date);
                                element.endDate = new Date(date.setMinutes(date.getMinutes() + element.traveltime)).toISOString();

                            });

                            $scope.results = flattened;
                        } else {
                            $scope.results = null;
                        }


                    });

                    //} else {
                    //    console.log("All fields are required")
                    //}
                };

                // Handle results



            }]);



