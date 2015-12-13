
'use strict';

/**
 * FlightSaver test.
 * 
 * This tests all the methods found in FlightSaver.
 * 
 * @Author: Casper Schultz
 * @Date: 8/12 2015
 */
describe('myApp module FlightSaver', function() {

    var FlightSaver;
  
    beforeEach(function (){  
      
      // We load the module 
      module('myApp');

      // We fetch the factory and the dependencies
      // Notice that _ in front and after is not needed
      inject(function(_FlightSaver_) {
        FlightSaver = _FlightSaver_;      
      });
    });
    
    // We make a test that fails if further functionality gets added.
    it('FlightSaver should have 2 methods', function () {
        expect(Object.keys(FlightSaver).length).toEqual(2);
    });
    
    // Test that it works and that the value is private
    it('FlightSave should store data in a private variable', function () {
         
      FlightSaver.set("test");
      
      // Ensure it can be set with setter
      expect(FlightSaver.get()).toEqual("test");
      
      // Ensure its private
      expect(FlightSaver.savedData).toEqual(undefined);
    });
});
