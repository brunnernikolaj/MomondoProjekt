'use strict';

/**
 * Flightfactory test.
 * 
 * This tests all the methods found in FlightFactory.
 * 
 * @Author: Casper Schultz
 * @Date: 8/12 2015
 */
describe('myApp module FlightFactory', function() {

    var FlightFactory, httpBackend, returnData;
  
    beforeEach(function (){  
      
      // We load the module 
      module('myApp');

      // We fetch the factory and the dependencies
      inject(function($httpBackend, _FlightFactory_) {
        FlightFactory = _FlightFactory_;      
        httpBackend = $httpBackend;
      });
      
      // We use the same response data for all the methods, 
      // so we just instantiate it here
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
    
    it('FlightFactory should contain 4 functions', function () {
            expect(Object.keys(FlightFactory).length).toEqual(4);
    });
    
    it('searchForFlights with no destination should return a promise that contains flightobjects', function () {
        
      // expectGET to make sure this is called once.
      httpBackend.expectGET('api/search/CPH/2015-12-21T23:00:00.000Z/2').respond(returnData);
      
      
      // We make the http call
      var returnedPromise = FlightFactory.searchForFlights('CPH', 'Tue Dec 22 2015 00:00:00 GMT+0100 (CET)', '2');

      // We need to store the result outside of the success function
      var result;
      
      returnedPromise.then(function(response) {
        result = response;
      });

      // We need to flush after a http call to use promises
      httpBackend.flush();

      // check the result. 
      expect(result).toEqual(returnData);
      expect("Just Fly").toEqual(returnData.airline);
      expect(result.flights.length).toEqual(returnData.flights.length);
    });
    
    it('searchForFlights should return a promise that contains flightobjects', function () {

      // expectGET to make sure this is called once.
      httpBackend.expectGET('api/search/CPH/ARN/2015-12-21T23:00:00.000Z/2').respond(returnData);

      // We make the http call
      var returnedPromise = FlightFactory.searchForFlights('CPH', 'Tue Dec 22 2015 00:00:00 GMT+0100 (CET)', '2', 'ARN');

      // We need to store the result outside of the success function
      var result;
      
      returnedPromise.then(function(response) {
        result = response;
      });

      // We need to flush after a http call to use promises
      httpBackend.flush();

      // check the result. 
      expect(result).toEqual(returnData);
      expect("Just Fly").toEqual(returnData.airline);
      expect(result.flights.length).toEqual(returnData.flights.length);
    }); 
});
