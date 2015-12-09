/* global airline */

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
angular.module('myApp').controller('AppCtrl', ['$scope', '$rootScope', '$location', 'LoginFactory', 'toastr', function ($scope, $rootScope, $location, LoginFactory, toastr) {

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
            toastr.success('', 'Du er nu logget ind');

            //Send the user to the page they were trying to visit, otherwise go to front page
            if (typeof $rootScope.savedLocation !== 'undefined') {
                $location.path($rootScope.savedLocation);
                $rootScope.savedLocation = undefined;
            } else {
                $location.path('/');
            }

        });

        $scope.$on('auth:failedLogin', function (event, args) {
            $scope.failedLogin = true;
            toastr.error('Login mislykkedes');
        });

        $scope.$on('auth:loggedOut', function (event, args) {
            $scope.authenticated = args.isLoggedIn();
            $scope.username = args.getUsername();
            toastr.success('', 'Du er nu logget ud');
            $location.path('/');
        });

        $rootScope.$on('$routeChangeStart', function (event, next) {
            if (next.auth) { //If view requires login
                if (!LoginFactory.isLoggedIn()) {
                    $rootScope.savedLocation = $location.url();
                    toastr.error('Du skal være logget ind for at forsætte');
                    $location.path('/login');
                }

                else if (!LoginFactory.isRole(next.auth)) {
                    toastr.error('Du har ikke adgang til dette område');
                    $location.path('/');
                }
            }
        });

        $scope.doLogout = function () {
            LoginFactory.doLogout();
        };
    }]);

angular.module('myApp').controller('LoginCtrl', ['$scope', '$rootScope', '$location', 'LoginFactory', 'toastr',
    function ($scope, $rootScope, $location, LoginFactory, toastr) {


        // Login logout app interface for talking with the auth object.
        $scope.doLogin = function () {

            LoginFactory.doLogin($scope.user)

            $scope.user = {};
        };
    }]);

angular.module('myApp').controller('BookingCtrl', ['$scope', '$location', 'toastr', "FlightSaver", 'ReservationFactory', 'LoginFactory',
    function ($scope, location, toastr, saver, ReservationFactory, LoginFactory) {

        $scope.flight = saver.get();
        $scope.reservation = {Passengers: []};

        for (var i = 0; i < $scope.flight.numberOfSeats; i++) {
            $scope.reservation.Passengers.push({});
        }

        $scope.reserveTickets = function () {
            $scope.reservation.flightID = $scope.flight.flightID;
            $scope.reservation.numberOfSeats = $scope.flight.numberOfSeats;

            $scope.reservation.userName = LoginFactory.getUsername();

            ReservationFactory.reservateExternalTickets($scope.reservation);

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
angular.module('myApp').controller("SearchCtrl", ['$scope', 'FlightFactory', 'FlightSaver', 'AirportFactory', 'toastr', function ($scope, FlightFactory, saver, AirportFactory, toastr) {
        
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
        /*
         * This part is for the autocomplete in the search form.
         */

        $scope.pickorigin = function(selected) {
            var airport = selected.split(",");
            airport = airport[2].trim();

            from = AirportFactory.getLocalStoredAirportByName(airport).IATAcode;
        }

        $scope.pickdestination = function (selected) {
            var airport = selected.split(",");
            airport = airport[2].trim();

            to = AirportFactory.getLocalStoredAirportByName(airport).IATAcode;
        }

        $scope.updateLocations = function (typed) {

            if (typed.length < 3) {
                $scope.locations = "";
                return;
            }

            AirportFactory.getAirportNiceNames(typed).then(function(res) {
                $scope.locations = res;
            });
        };

        /*
         * This part is for the booking of a flight
         */

        $scope.selectFlight = function (flight) {
            saver.set(flight);
        };
        
        
        // If the user has already made a search, we get that on load
        if (FlightFactory.getLastSearch().result != null) {
            var lastSearch = FlightFactory.getLastSearch();
            $scope.priceSlider.options.ceil = lastSearch.max;
            $scope.priceSlider.max = lastSearch.max;
            $scope.results = lastSearch.result;
        }
        
        // handle incomming data
        $scope.searchFlights = function () {
            FlightFactory.searchForFlights(from, to, $scope.search.date, $scope.search.seats).then(function(res) {

                if (res[0] != undefined) {
                    unpackFlights(res);
                } else {
                    $scope.results = null;
                    toastr.info('Der blev ikke fundet nogle flyafgange. Prøv venligst en ny søgning');
                }
            });
        };

        //Function for unpacking resultdata from the server
        var unpackFlights = function (result) {

            FlightFactory.unpackFlights(result).then(function(res) {

                $scope.priceSlider.options.ceil = res.max;
                $scope.priceSlider.max = res.max;

                // We return the result here, then append the names once they are fetched
                $scope.results = res.arr;

                FlightFactory.attachAirportNames(res.arr);

            });
        };
    }]);

/**
 * Controller for signup form 
 * 
 * @Author: Nikolaj Brünner
 * @Date: 7/12 2015
 * 
 */
angular.module('myApp').controller('SignupCtrl', ['$scope', 'SignupFactory', 'toastr', function ($scope, SignupFactory, toastr) {

        $scope.user = {};

        $scope.signup = function () {
            SignupFactory.signup($scope.user).then(function (result) {
                toastr.success(result.data);
            }, function (error) {
                toastr.error(error.data);
            });
        };
    }]);

/**
 * Controller for user reservation page
 * 
 * @Author: Nikolaj Brünner
 * @Date: 7/12 2015 
 * 
 */
angular.module('myApp').controller('MyReservationsCtrl', ['$scope', 'toastr', 'ReservationFactory', 'LoginFactory',
    function ($scope, toastr, ReservationFactory, LoginFactory) {

        ReservationFactory.getByUser(LoginFactory.getUsername()).then(function (result) {
            $scope.reservations = result.data;
        });
    }]);

angular.module('myApp').controller('AdminCtrl', ['$scope','ReservationFactory', 'toastr',
    function ($scope, ReservationFactory, toastr) {
        
        ReservationFactory.getAll().then(function (result) {
            $scope.reservations = result.data;
        });
    }]);