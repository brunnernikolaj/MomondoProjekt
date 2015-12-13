'use strict';

/**
 * ReservationFactoty test.
 * 
 * This tests all the methods found in ReservationFactoty.
 * 
 * @Author: Casper Schultz
 * @Date: 8/12 2015
 */
describe('myApp module ReservationFactory', function() {

    var ReservationFactory, httpBackend;
  
    beforeEach(function (){  
      
      // We load the module 
      module('myApp');

      // We fetch the factory and the dependencies
      inject(function($httpBackend, _ReservationFactory_) {
        ReservationFactory = _ReservationFactory_;      
        httpBackend = $httpBackend;
      });
    });

    // make sure no expectations were missed in the tests.
    // (e.g. expectGET or expectPOST)
    afterEach(function() {
      httpBackend.verifyNoOutstandingExpectation();
      httpBackend.verifyNoOutstandingRequest();
    });
    
    it('ReservationFactoty should contain 4 functions', function () {
            expect(Object.keys(ReservationFactory).length).toEqual(4);
    });
});

