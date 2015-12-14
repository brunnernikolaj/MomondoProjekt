'use strict';

/**
 * Here we init the module and all its dependencies. 
 * 
 * To read more about the concept we are using to structure our app,
 * read here: http://briantford.com/blog/huuuuuge-angular-apps
 * 
 * @param {type} param
 */
angular.module('myApp', [
  'ngRoute',
  'ngAnimate',
  'autocomplete',
  'toastr',
  'rzModule',
  'ui.bootstrap',
  'security',
  'myApp.view2',
  'myApp.view3',
]);

/**
 * Route configuration for the app
 * 
 * @param {type} $routeProvider
 */
angular.module('myApp').config(['$routeProvider', '$httpProvider','toastrConfig', function($routeProvider, $httpProvider, toastrConfig) {
    
    /**
     * We add auth headers on every http request. See the authentication
     * module for more info.
     */
    $httpProvider.interceptors.push('AuthInterceptor');
        
    $routeProvider
    .when('/login', {
      templateUrl: 'app/templates/loginForm.html',
      controller: 'LoginCtrl',
      controllerAs : 'ctrl'
    })
    .when('/signup', {
      templateUrl: 'app/templates/signupForm.html',
      controller: 'SignupCtrl'
    })
    .when('/doc', {
      templateUrl: 'aap/templates/documentation.html'
    })
    .when('/searchresult', {
      templateUrl: 'app/templates/searchresult.html',
      controller: 'SearchCtrl'
    })
    .when('/booking', {
      templateUrl: 'app/templates/booking.html',
      controller: 'BookingCtrl',
      auth: 'User'
    })
     .when('/my-reservations', {
      templateUrl: 'app/templates/my-reservations.html',
      controller: 'MyReservationsCtrl',
      auth: 'User'
    })
    .when('/admin-control', {
      templateUrl: 'app/templates/admin.html',
      controller: 'AdminCtrl',
      auth: 'Admin'
    })
    .when('/', {
      templateUrl: 'app/templates/searchresult.html',
      controller: 'SearchCtrl'
    })
    // Make frontpage after Sprint 1, is over.
    // .when('/', {
    //     templateUrl: 'app/templates/frontpage.html',
    //     controller: 'FrontpageCtrl',
    //     controllerAs : 'forntpageCtrl'
    // })
    .otherwise({redirectTo: '/'});
    
    angular.extend(toastrConfig, {
    positionClass: 'toast-bottom-left',
    preventDuplicates: false,
    preventOpenDuplicates: false,
    target: 'body'
  });

}]);


/**
 * I know this seems messy, bun in order to be able to test, we need to have this already
 * loaded into the dom and we do this by using angulars $templateCache service for get/set
 * of html templates. 
 * 
 * It should be put in a seperate file, and I will look into that, once
 * the test is up and running.
 * 
 * and by the way, .run runs after the entire angular js app has been bootstrapped
 * 
 * @param {type} param
 */
angular.module('myApp').run(function($templateCache) {
    $templateCache.put('flightResult.html', 
        '<div class="col-xs-12 col-sm-9">' +
        '<div class="clearfix departure">' +
            '<div class="row selskab">' +
                '<div class="col-xs-12">' +
                 '   <span class="selskabsnavn" id="tester">{{flight.airline}}</span>' +
               ' </div>' +
          '  </div>' +
           ' <div class="col-xs-3 col-sm-3 col-md-2 company">' +
           '     <img src="http://placehold.it/64x64" class="img-responsive">' +
           ' </div>' +
           ' <div class="col-xs-9 col-sm-9 col-md-3 departureinfo">' +
           '     <h4>{{flight.origin}} {{flight.date| date:\'HH:mm\'}} <small>{{flight.originCity}}</small></h4>' +
           ' </div>' +
          '  <div class="col-xs-3 col-sm-3 col-md-3 traveltime">' +
           '     <h5 class="time">{{flight.traveltime| traveltime}}</h5>' +
         '       <div class="timeline"></div>' +
         '       <p>direkte</p>' +
         '   </div>' +
        '    <div class="col-xs-9 col-sm-9 col-md-3 arrivalinfo">' +
        '       <h4>{{flight.endDate| date:\'HH:mm\'}} {{flight.destination}} <small>{{flight.destinationCity}}</small></h4>' +
        '    </div>' +
       ' </div><!-- /.row.departure -->' +
    '</div><!-- /.col-xs-12.col-sm-8 -->' +
    '<div class="col-xs-12 col-sm-3 pricearea">' +
      '  <!--<p class="airline-heading">{{flight.airline}}</p>-->' +
       ' <div class="row prices">' +
       '     <div class="col-xs-12 grouppassengers" ng-hide="flight.numberOfSeats <= 1">{{flight.totalPrice}} EUR <i class="fa fa-users"></i></div>' +
       '    <div class="col-xs-12 singlepassenger">{{flight.totalPrice / flight.numberOfSeats}} EUR <i class="fa fa-user"></i></div>' +
       ' </div>' +
       ' <a ng-click="reserve(flight)" href="#/booking" class="btn btn-success"><i class="fa fa-plus"></i> Book Nu</a>' +
    '</div>'
        );
});


angular.module('myApp').run(function($templateCache) {
    $templateCache.put('flightSearhForm.html', 
        '<form class="form-horizontal" ng-model="search">' +
                   ' <div class="col-sm-3">' +
                        ' <label class="control-label">Afrejse</label>' +
                      '   <div class="input-group">' +
                         '    <span class="input-group-addon"><i class="fa fa-plane"></i></span>' +
                       '      <autocomplete ng-model="search.from" attr-placeholder="Origin" data="locations" on-select="pickorigin" on-type="updateLocations" required></autocomplete>' +
                      '   </div>' +
                    ' </div>' +
                   '  <div class="col-sm-3">' +
                        ' <label class="control-label">Destination</label>' +
                       '  <div class="input-group">' +
                        '    <span class="input-group-addon"><i class="fa fa-plane"></i></span>' +
                       '     <autocomplete ng-model="search.to" attr-placeholder="Destination" data="locations" on-select="pickdestination" on-type="updateLocations"></autocomplete>' +
                       ' </div>' +
                   ' </div>' +
                  '  <div class="col-sm-3">' +
                  '      <label class="control-label">Dato</label>' +
                   '     <div class="input-group">' +
                   '         <span class="input-group-addon"><i class="fa fa-calendar"></i></span>' +
                   '         <input type="date" class="form-control" ng-model="search.date" placeholder="2/12/2015">' +
                  '      </div>' +
                 '   </div> ' +
                   ' <div class="col-sm-3">' +
                     '   <label class="control-label">Passagerer</label>' +
                      '  <div class="input-group">' +
                      '      <span class="input-group-addon"><i class="fa fa-user"></i></span>' +
                     '       <input type="number" class="form-control" ng-model="search.seats" name="adults" min="0" value="0">' +
                    '    </div>' +
                   ' </div>' +
                  '  <div class="col-xs-12">' +
                   '     <div class="input-group col-sm-3 formbutton">' +
                   '         <button type="submit" ng-click="searchFlights()" id="search"  class="btn btn-primary col-xs-12"><i class="fa fa-search"></i> Find Fly</button>' +
                   '     </div>' +
                   ' </div>' +
             '   </form>'
    );
});
