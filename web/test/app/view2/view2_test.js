describe('myApp.view2 view2Ctrl', function () {

  var scope, httpBackendMock, ctrl;
  var testResponse = {message: "Test Message Mock"};

  beforeEach(module('myApp.view2'));

  beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
    httpBackendMock = $httpBackend;
    httpBackendMock.expectGET('api/demouser').respond(testResponse);
    scope = $rootScope.$new();
    ctrl = $controller('View2Ctrl', {$scope: scope});
  }));

  it('Should fetch a test message', function () {
    expect(scope.data).toBeUndefined();
    httpBackendMock.flush();
    expect(scope.data).toEqual("Test Message Mock");
  });

});

