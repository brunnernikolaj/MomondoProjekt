describe('myApp.view3 view3Ctrl', function () {

  var scope, httpBackendMock, ctrl;
  var testResponse = {message: "Test Message Mock", serverTime: "10-23-2015 17:35:21"};

  beforeEach(module('myApp.view3'));

  beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
    httpBackendMock = $httpBackend;
    httpBackendMock.expectGET('api/demoadmin').respond(testResponse);
    scope = $rootScope.$new();
    ctrl = $controller('View3Ctrl', {$scope: scope});
  }));
  
  //describe('view2 controller', function () {
    it('Should fetch a test message', function () {
      expect(scope.info).toBeUndefined();
      httpBackendMock.flush();
      expect(scope.data.message).toEqual("Test Message Mock");
      expect(scope.data.serverTime).toEqual("10-23-2015 17:35:21");
    //});
  });
});

