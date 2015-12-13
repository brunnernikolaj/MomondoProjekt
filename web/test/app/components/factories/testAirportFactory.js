'use strict';

/**
 * AirportFactoty test.
 * 
 * This tests all the methods found in AirportFactoty.
 * 
 * @Author: Casper Schultz
 * @Date: 8/12 2015
 */
describe('myApp module AirportFactory', function() {

    var AirportFactory, httpBackend;
  
    beforeEach(function (){  
      
      // We load the module 
      module('myApp');

      // We fetch the factory and the dependencies
      inject(function($httpBackend, _AirportFactory_) {
        AirportFactory = _AirportFactory_;      
        httpBackend = $httpBackend;
      });
    });

    // make sure no expectations were missed in the tests.
    // (e.g. expectGET or expectPOST)
    afterEach(function() {
      httpBackend.verifyNoOutstandingExpectation();
      httpBackend.verifyNoOutstandingRequest();
    });
    
    it('AirportFactory should contain 6 functions', function () {
            expect(Object.keys(AirportFactory).length).toEqual(6);
    });
    
    it('getAirportsByCity should return a promise that contains a list of airports', function () {
      
      var responseData = [
        {
          "id": 609,
          "name": "Kastrup",
          "city": "Copenhagen",
          "country": "Denmark",
          "IATAcode": "CPH",
          "ICAOcode": "EKCH",
          "latitude": "55.617917",
          "longitude": "12.655972",
          "altitude": "17",
          "timezone": "1"
        },
        {
          "id": 617,
          "name": "Roskilde",
          "city": "Copenhagen",
          "country": "Denmark",
          "IATAcode": "RKE",
          "ICAOcode": "EKRK",
          "latitude": "55.585567",
          "longitude": "12.131428",
          "altitude": "146",
          "timezone": "1"
        },
        {
          "id": 8489,
          "name": "Central",
          "city": "Copenhagen",
          "country": "Denmark",
          "IATAcode": "ZGH",
          "ICAOcode": "\\N",
          "latitude": "55.672778",
          "longitude": "12.564444",
          "altitude": "16",
          "timezone": "1"
        }
      ];
      
      // expectGET to make sure this is called once.
      httpBackend.expectGET('api/airport/city/copen').respond(responseData);

      // We make the http call
      var returnedPromise = AirportFactory.getAirportsByCity('copen');

      // We need to store the result outside of the success function
      var result;
      
      returnedPromise.then(function(response) {
        result = response.data;
      });

      // We need to flush after a http call to use promises
      httpBackend.flush();

      // check the result.
      expect(result.length).toEqual(3);
      expect(result[0].name).toEqual("Kastrup");
      expect(Object.keys(result[0]).length).toEqual(10);
    }); 
    
    it('getAirportNiceNames should throw an error when called with less then 3 characters', function () {

        function errorFunctionWrapper() {
            AirportFactory.getAirportNiceNames('cp');
        }
        
        expect(errorFunctionWrapper).toThrow();
    }); 
    
    it('getAirportNiceNames returns a promise with correct data', function () {
      
      var responseData = [
        {
          "id": 609,
          "name": "Kastrup",
          "city": "Copenhagen",
          "country": "Denmark",
          "IATAcode": "CPH",
          "ICAOcode": "EKCH",
          "latitude": "55.617917",
          "longitude": "12.655972",
          "altitude": "17",
          "timezone": "1"
        },
        {
          "id": 617,
          "name": "Roskilde",
          "city": "Copenhagen",
          "country": "Denmark",
          "IATAcode": "RKE",
          "ICAOcode": "EKRK",
          "latitude": "55.585567",
          "longitude": "12.131428",
          "altitude": "146",
          "timezone": "1"
        },
        {
          "id": 8489,
          "name": "Central",
          "city": "Copenhagen",
          "country": "Denmark",
          "IATAcode": "ZGH",
          "ICAOcode": "\\N",
          "latitude": "55.672778",
          "longitude": "12.564444",
          "altitude": "16",
          "timezone": "1"
        }
      ];
      
      var expectedResult = [
          "Denmark, Copenhagen, Kastrup", 
          "Denmark, Copenhagen, Roskilde", 
          "Denmark, Copenhagen, Central"
      ];
      
      // expectGET to make sure this is called once.
      httpBackend.expectGET('api/airport/city/copen').respond(responseData);

      // We make the http call
      var returnedPromise = AirportFactory.getAirportNiceNames('copen');

      // We need to store the result outside of the success function
      var result;
      
      returnedPromise.then(function(response) {
        result = response;
      });

      // We need to flush after a http call to use promises
      httpBackend.flush();

      // check the result.
      expect(expectedResult).toEqual(result);
    });
    
    it('getAirportByIATA should return a promise that contains an airport', function () {
      
      var responseData = {
        "id": 609,
        "name": "Kastrup",
        "city": "Copenhagen",
        "country": "Denmark",
        "IATAcode": "CPH",
        "ICAOcode": "EKCH",
        "latitude": "55.617917",
        "longitude": "12.655972",
        "altitude": "17",
        "timezone": "1"
      };
      
      // expectGET to make sure this is called once.
      httpBackend.expectGET('api/airport/cph').respond(responseData);

      // We make the http call
      var returnedPromise = AirportFactory.getAirportByIATA('cph');

      // We need to store the result outside of the success function
      var result;
      
      returnedPromise.then(function(response) {
        result = response.data;
      });

      // We need to flush after a http call to use promises
      httpBackend.flush();

      // check the result.
      expect(result.name).toEqual("Kastrup");
      expect(Object.keys(result).length).toEqual(10);
    });
    
    it('getAirportByIATA should return a promise that contains an airport', function () {
      
      var responseData = {
        "id": 609,
        "name": "Kastrup",
        "city": "Copenhagen",
        "country": "Denmark",
        "IATAcode": "CPH",
        "ICAOcode": "EKCH",
        "latitude": "55.617917",
        "longitude": "12.655972",
        "altitude": "17",
        "timezone": "1"
      };
      
      // expectGET to make sure this is called once.
      httpBackend.expectGET('api/airport/cph').respond(responseData);

      // We make the http call
      var returnedPromise = AirportFactory.getAirportByIATA('cph');

      // We need to store the result outside of the success function
      var result;
      
      returnedPromise.then(function(response) {
        result = response.data;
      });

      // We need to flush after a http call to use promises
      httpBackend.flush();

      // check the result.
      expect(result.name).toEqual("Kastrup");
      expect(Object.keys(result).length).toEqual(10);
    }); 
    
    it('getAirportByIATA should return a promise that contains an airport', function () {
      
      var responseData = {
        "valid": "true"
      };
      
      // expectGET to make sure this is called once.
      httpBackend.expectGET('api/airport/valid/cph').respond(responseData);

      // We make the http call
      var returnedPromise = AirportFactory.isValidAirport('cph');

      // We need to store the result outside of the success function
      var result;
      
      returnedPromise.then(function(response) {
        result = response.data;
      });

      // We need to flush after a http call to use promises
      httpBackend.flush();

      // check the result.
      expect(result.valid).toEqual("true");
      expect(Object.keys(result).length).toEqual(1);
    });
});

