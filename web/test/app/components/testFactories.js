'use strict';

describe('myApp module', function() {

    var FlightFactoty, httpBackend, returnData;
  
    beforeEach(function (){  
      
      // We load the module 
      module('myApp');

      // We fetch the factory and the dependencies
      inject(function($httpBackend, _FlightFactoty_) {
        FlightFactoty = _FlightFactoty_;      
        httpBackend = $httpBackend;
      });
      
      // We use the same response data for all the methods
      returnData = {
        "airline": "Just Fly",
        "flights": [
          {
            "origin": "CPH",
            "destination": "GVA",
            "flightID": "JF115",
            "numberOfSeats": 2,
            "traveltime": 0,
            "totalPrice": 2,
            "date": "2016-04-06T22:00:00.000Z"
          },
          {
            "origin": "CPH",
            "destination": "GVA",
            "flightID": "JF620",
            "numberOfSeats": 2,
            "traveltime": 80,
            "totalPrice": 2,
            "date": "2016-04-06T22:00:00.000Z"
          }
        ]
      };
    });

    // make sure no expectations were missed in the tests.
    // (e.g. expectGET or expectPOST)
    afterEach(function() {
      httpBackend.verifyNoOutstandingExpectation();
      httpBackend.verifyNoOutstandingRequest();
    });

    
    it('should receive a promise that contains flightobjects', function () {

      // expectGET to make sure this is called once.
      httpBackend.expectGET('api/search/CPH/2015-12-28T18:19:16+01:00/2').respond(returnData);

      // make the call.
      var returnedPromise = FlightFactoty.searchWithNoDestination('CPH', '2015-12-28T18:19:16+01:00', '2');

      // set up a handler for the response, that will put the result
      var result;
      
      returnedPromise.then(function(response) {
        result = response.data;
      });

      // We need to flush after a http call to use promises
      httpBackend.flush();

      // check the result. 
      expect(result).toEqual(returnData);
    });  
  
});
