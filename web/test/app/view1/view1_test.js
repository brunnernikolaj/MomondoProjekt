'use strict';

describe('myApp.view1 module', function() {

  beforeEach(module('myApp.view1'));
  
 //Mocks for the test
  beforeEach(module({
    InfoFactory: { getInfo: function() {return  "Factory"; }},
    InfoService: { getInfo: function() {return  "Service"; }}
  }));

  describe('view1 controller', function(){
    
    it('should use View1Ctrl', inject(function($controller) {
      var view1Ctrl = $controller('View1Ctrl');
      expect(view1Ctrl).toBeDefined();
    }));

  });
});