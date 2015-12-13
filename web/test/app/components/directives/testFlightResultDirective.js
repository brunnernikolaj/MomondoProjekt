'use strict';

describe('myApp module FlightResult Directive', function() {

    var compile, scope, directiveElem;

    beforeEach(function(){
      module('myApp');

      inject(function($compile, $rootScope){
        compile = $compile;
        scope = $rootScope.$new();
      });

      directiveElem = getCompiledElement();
    });

    function getCompiledElement(){
      var element = angular.element('<flight-result result="flight"></flight-result>');
      var compiledElement = compile(element)(scope);
      scope.$digest();
      return compiledElement;
    };
    
    it('should have updated text in span', function () {
        
        // The scope object that we pass to the directive
        var returnData = {
            $$hashKey: "object:72",
            airline: "Just Fly",
            date: "2015-12-21T23:00:00.000Z",
            destination: "ARN",
            destinationCity: "Stockholm",
            destinationName: "Arlanda",
            endDate: "2015-12-21T23:20:00.000Z",
            flightID: "JF725",
            numberOfSeats: 2,
            origin: "CPH",
            originCity: "Copenhagen",
            originName: "Kastrup",
            totalPrice: 126.6,
            traveltime: 20
        };
        
        scope.flight = returnData;
        
        // we apply the scope (apply is globally, while digest is locally)
        scope.$apply();
        
        // Airline is easy to test, since theres only 1 span
        var airline = directiveElem.find('span').text();
        expect(airline).toBeDefined();
        expect(airline).toEqual(scope.flight.airline);
        
        // WOW! This is messy, but with jqlite its quite hard to get the propper selector. 
        // so we will just retrieve them all and then see that the data is correct...
        var city = directiveElem.find('h4').text();
        expect(city).toEqual(returnData.origin + " 00:00 " + returnData.originCity + "00:20 " + returnData.destination + " " + returnData.destinationCity)
    });
});

