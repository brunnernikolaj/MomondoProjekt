/**
 * Main app controller
 * 
 * This is the main controller for the wep application. This controller 
 * handles all logic that the entire site depends on, like handling the login
 * setting titles and application name.
 * 
 * @Author: Casper Schultz
 * @Date: 7/12 2015
 * 
 * @param {string} Controllername
 * @param {type} Angular dependencies
 */
angular.module('myApp').controller('AppCtrl', ['$scope', '$location', 'LoginFactory', function ($scope, $location, LoginFactory) {
    
    // App variables
    $scope.title = "JustFly";
    $scope.project = "JustFly";
    
    // Login variables
    $scope.authenticated = LoginFactory.isLoggedIn();
    $scope.username = LoginFactory.getUsername();
    
    // We use angulars observer pattern to watch for login / logout events.
    $scope.$on('auth:loggedIn', function (event, args) {
        $scope.failedLogin = false;
        $scope.authenticated = args.isLoggedIn();
        $scope.username = args.getUsername();
        $location.path('/');
    });
    
    $scope.$on('auth:failedLogin', function(event, args) {
       $scope.failedLogin = true; 
    });

    $scope.$on('auth:loggedOut', function (event, args) {
        $scope.authenticated = args.isLoggedIn();
        $scope.username = args.getUsername();
        $location.path('/');
    });
    
    // Login logout app interface for talking with the auth object.
    $scope.doLogin = function () {
        LoginFactory.doLogin($scope.user);
        $scope.user = {};
    };

    $scope.doLogout = function () {
        LoginFactory.doLogout();
    };
}]);


angular.module('myApp').controller('BookingCtrl', ['$scope', "flightSaver",'ReservationFactoty','LoginFactory', function ($scope, saver,ReservationFactoty,LoginFactory) {
    //der er data her, der skal bare laves mere kode
    $scope.flight = saver.get();
    $scope.reservation = {Passengers:[]};

    for (var i = 0; i < $scope.flight.numberOfSeats; i++) {
        $scope.reservation.Passengers.push({});
    }

    $scope.reserveTickets = function () {
        $scope.reservation.flightID = $scope.flight.flightID;
        $scope.reservation.numberOfSeats = $scope.flight.numberOfSeats;

        if ($scope.flight.airline === "Just Fly"){
            ReservationFactoty.reservateTickets($scope.reservation)
        } else {
            ReservationFactoty.reservateExternalTickets($scope.reservation)
        }                    
    };
}]);

/**
 * Search form controller.
 * 
 * @Author: Casper Schultz
 * @Date: 4/12 2015
 * 
 * @returns {undefined}
 */
angular.module('myApp').controller("SearchCtrl", ['$scope', 'FlightFactoty', 'flightSaver', 'AirportFactoty', 'toastr', function ($scope, FlightFactoty, saver, AirportFactoty, toastr) {
        
    var from, to;
    $scope.cities = [];
    $scope.airports = undefined;

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
        
        
        $scope.pickorigin = function(selected) {

            var airport = selected.split(",");
            airport = airport[2].trim();

            angular.forEach($scope.airports, function(value, key) {
                if (value.name === airport) {
                    from = value.IATAcode;
                }
            });
        }

        $scope.pickdestination = function(selected) {
            var airport = selected.split(",");
            airport = airport[2].trim();

            angular.forEach($scope.airports, function(value, key) {
                if (value.name === airport) {
                    to = value.IATAcode;
                }
            });
        }
        
        $scope.invalidFrom = false;
        $scope.test = function() {
            $scope.invalidFrom = false;
            console.log("test")
        }

        
        /**
         * Fetches a list of airports that match the given name.
         * 
         * @param {type} typed
         * @returns {undefined}
         */
        $scope.updateCities = function (typed) {
            
            // We only want to fetch something, after theres atleast 3 letters
            if (typed.length > 2) {
                AirportFactoty.getAirportsByCity(typed).then(function(res) {

                    // Used to display nice names
                    $scope.cities = [];

                    // Used for the IATA / other codes 
                    // We split it up, since we need both the american and the IATA code
                    $scope.airports = res.data;

                    for (var i = 0, l = res.data.length; i < l; i++) {
                        $scope.cities.push(res.data[i].country + ", " + res.data[i].city + ", " + res.data[i].name)
                    }
                })
            }
        }

        $scope.selectFlight = function (flight) {
            saver.set(flight);
        };

        // handle incomming data
        $scope.searchFlights = function () {

            var searchQuery = $scope.search;
            
            // Basic checking for empty values
            if (from == undefined || from == "" || $scope.search == undefined || $scope.search.date == undefined || $scope.search.date == "") {
                toastr.error('Alle felter skal udfyldes');
                return;
            }
            
            
            var date = new Date(searchQuery.date).toISOString();
            
            if (to) {
                FlightFactoty.searchWithDestination(from, to, date, searchQuery.seats).then(unpackFlights);
            } else {
                FlightFactoty.searchWithNoDestination(from, date, searchQuery.seats).then(unpackFlights);
            }
        };

        //Function for unpacking resultdata from the server
        var unpackFlights = function (result) {
            if (result.data[0] != null) {
                var maxValue = 0;

                result.data.forEach(function (airline, index) {

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
                var flights = result.data.map(airline => airline.flights);
                var flattened = [];
                for (var i = 0; i < flights.length; ++i) {
                    var current = flights[i];
                    for (var j = 0; j < current.length; ++j)
                        flattened.push(current[j]);
                }
                
                // Filter the results
                $scope.priceSlider.options.ceil = maxValue;
                $scope.priceSlider.max = maxValue;
                
                // We return the result here, then append the names once they are fetched
                $scope.results = flattened;
                
                // Airports we should fetch
                var airportCodes = [];
                
                // First we gotta loop through and get all the different iata codes
                // and prepare the flight object for the new data.
                for (var i = 0, l = flattened.length;  i < l; i++) {
                    
                    // Prepare for populating later
                    flattened[i].originCity = "";
                    flattened[i].originName = "";
                    flattened[i].destinationCity = "";
                    flattened[i].destinationName = "";
                    
                    if (airportCodes.indexOf(flattened[i].origin) == -1) {
                        airportCodes.push(flattened[i].origin);
                    }
                    
                    if (airportCodes.indexOf(flattened[i].destination) == -1) {
                        airportCodes.push(flattened[i].destination);
                    }
                }
                
                // Now we fetch the airport names.
                for (var c = 0, d = airportCodes.length; c < d; c++) {
                    AirportFactoty.getAirportByIATA(airportCodes[c]).then(function(res) {
                        
                        for (var j = 0; j < flattened.length; j++) {
                            if (flattened[j].origin == res.data.IATAcode) {
                                flattened[j].originName = res.data.name;
                                flattened[j].originCity = res.data.city;
                            }
                            
                            if (flattened[j].destination == res.data.IATAcode) {
                                flattened[j].destinationName = res.data.name;
                                flattened[j].destinationCity = res.data.city;
                            }
                        }
                    });
                }
                
                
                
            } else {
                $scope.results = null;
            }
        };
    }]);

app.controller('SignupCtrl', ['$scope','SignupFactory','toastr', function ($scope, SignupFactory,toastr) {

    $scope.user = {};

    $scope.signup = function () {
        SignupFactory.signup($scope.user).then(function (result) {
            toastr.success(result.data);
        }, function (error) {
            toastr.error(error.data);
        });       
    };
}]);

