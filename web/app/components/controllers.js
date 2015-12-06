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
            }])

        .controller('BookingCtrl', ['$scope',"flightSaver", function ($scope,saver) {
                //der er data her, der skal bare laves mere kode
                $scope.flight = saver.get();
            }])

        /**
         * Search form controller.
         * 
         * @Author: Casper Schultz
         * @Date: 4/12 2015
         * 
         * @returns {undefined}
         */
        .controller("SearchCtrl", ['$scope', 'FlightFactoty', 'flightSaver', function ($scope, FlightFactoty, saver) {
                $scope.priceSlider = {
                    min: 0,
                    max: 10,
                    options: {
                        floor: 0,
                        ceil: 0
                    }
                };

                $scope.durationSlider = {
                    value: 200,
                    options: {
                        floor: 0,
                        ceil: 450,
                        step: 5,
                    }
                };

                $scope.filterSearch = function (min, max, duration) {
                    return function (item) {
                        if (item['traveltime'] > duration)
                            return false;

                        return item['totalPrice'] >= min && item['totalPrice'] <= max;
                    }
                }

                $scope.cities = ["CPH", "SXF"];
                //For auto complete
                $scope.updateCities = function (typed) {
                    $scope.cities = ["CPH", "SXF"];
                }

                $scope.selectFlight = function (flight) {
                    saver.set(flight);
                };

                // handle incomming data
                $scope.searchFlights = function () {
                    var searchQuery = $scope.search;

                    var date = new Date(searchQuery.date).toISOString();

                    if (searchQuery.to) {
                        FlightFactoty.searchWithDestination(searchQuery.from, searchQuery.to, date, searchQuery.seats).then(unpackFlights);
                    } else {
                        FlightFactoty.searchWithNoDestination(searchQuery.from, date, searchQuery.seats).then(unpackFlights);
                    }

                };

                //Function for unpacking resultdata from the server
                var unpackFlights = function (result) {
                    if (result.data[0] != null) {
                        //Select all flight arrays and then flatten them to one array
                        var flights = result.data.map(airline => airline.flights);
                        var flattened = [];
                        for (var i = 0; i < flights.length; ++i) {
                            var current = flights[i];
                            for (var j = 0; j < current.length; ++j)
                                flattened.push(current[j]);
                        }

                        var maxValue = 0;

                        //Add end date for each flight, and find max price

                        flattened.forEach(function (element, index) {
                            var date = new Date(element.date);
                            element.endDate = new Date(date.setMinutes(date.getMinutes() + element.traveltime)).toISOString();

                            if (element.totalPrice > maxValue) {
                                maxValue = element.totalPrice;
                            }
                        });
                        $scope.priceSlider.options.ceil = maxValue;
                        $scope.priceSlider.max = maxValue;

                        $scope.results = flattened;
                    } else {
                        $scope.results = null;
                    }
                }
            }]);



