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

        .controller('FrontpageCtrl', ["SearchFactory", "InfoService", function (searchService, InfoService) {

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
        .controller("SearchCtrl", ['$scope', function($scope) {
               
            // handle incomming data
            $scope.searchFlights = function() {
                
                if ($scope.search.to !== undefined && $scope.search.from !== undefined && $scope.search.date !== undefined && $scope.search.date !== null && $scope.search.seats !== undefined) {
                    console.log($scope.search);
                } else {
                    
                }
            };
            
            // Handle 
            
            
                        
        }]);



