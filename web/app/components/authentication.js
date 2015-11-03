angular.module('myApp.security', [])

.controller('AppLoginCtrl', function ($scope, $rootScope, $http, $window, $location) {

  function url_base64_decode(str) {
    var output = str.replace('-', '+').replace('_', '/');
    switch (output.length % 4) {
      case 0:
        break;
      case 2:
        output += '==';
        break;
      case 3:
        output += '=';
        break;
      default:
        throw 'Illegal base64url string!';
    }
    return window.atob(output); //polifyll https://github.com/davidchambers/Base64.js
  }

  //Other controller emits the logOutEvent to force a logout
  $scope.$on('logOutEvent', function (event, args) {
    $scope.error = "Your session timed out. Please login again";
    $scope.logout();
  });

  $scope.isActive = function (viewLocation) {
    return viewLocation === $location.path();
  };

  $scope.username = "";
  $scope.isAuthenticated = false;
  $scope.isAdmin = false;
  $scope.isUser = false;
  $scope.message = '';
  $scope.error = null;

  $scope.login = function () {
      
    $http
            .post('api/login', $scope.user)
            .success(function (data, status, headers, config) {
              $window.sessionStorage.token = data.token;
              $scope.isAuthenticated = true;
              var encodedProfile = data.token.split('.')[1];
              var profile = JSON.parse(url_base64_decode(encodedProfile));
              $scope.username = profile.username;
              var roles = profile.roles.split(",");
              roles.forEach(function (role) {
                if(role === "Admin"){
                   $scope.isAdmin =true;
                 }
                if(role === "User"){
                   $scope.isUser = true;
                 }
              });
              $scope.error = null;
              $location.path("#/view1");
            })
            .error(function (data, status, headers, config) {
              // Erase the token if the user fails to log in
              delete $window.sessionStorage.token;
              $scope.isAuthenticated = false;
              $scope.isAdmin = false;
              $scope.isUser = false;
              $scope.username = "";
              $scope.error = data.error;
              //$scope.logout();  //Clears an eventual error message from timeout on the inner view
            });
  };

  $rootScope.logout = function () {
    $scope.isAuthenticated = false;
    $scope.isAdmin = false;
    $scope.isUser = false;
    delete $window.sessionStorage.token;
    $location.path("#/view1");
  };

  //This sets the login data from session store if user pressed F5 (You are still logged in)
  var init = function () {
    var token = $window.sessionStorage.token;
    if (token) {
      $scope.isAuthenticated = true;
      var encodedProfile = token.split('.')[1];
      var profile = JSON.parse(url_base64_decode(encodedProfile));
      $scope.username = profile.username;
      $scope.isAdmin = profile.role === "Admin";
      $scope.isUser = !$scope.isAdmin;
      $scope.error = null;
    }
  };
// and fire it after definition
  init();
}).
  factory('authInterceptor', function ($rootScope, $q, $window) {
    return {
      request: function (config) {
        config.headers = config.headers || {};
        if ($window.sessionStorage.token) {
          config.headers.Authorization = 'Bearer ' + $window.sessionStorage.token;
        }
        $rootScope.error = "";
        return config;
      },
      responseError: function (rejection) {
        var err = rejection.data;
        if(err == null){
          $rootScope.error = "Unknown error while trying to connect to the server";
          var err= "\n########################################################################\n"+
          "Unknown error while trying to connect to the server\n"+
          "Did you execute the AngularSeedClient project, and not The AngularSeedServer Project?\n"+
          "#######################################################################\n";
          throw err;
          return;
        }
        $rootScope.error = err.error.code +": ";
        
        if (err.error.code === 401) {
          $rootScope.error += " You are are not Authenticated - did you log on to the system"
        }
        else{
           $rootScope.error +=  err.error.message;
        }
        if (err.error.code === 403) {
         
        }
       
        return $q.reject(rejection);
      }
    };
  });
