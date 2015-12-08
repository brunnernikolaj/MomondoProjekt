module.exports = function(config){
  config.set({

    basePath : './',

    files : [
      'bower_components/angular/angular.js',
      'bower_components/angular-route/angular-route.js',
      'bower_components/angular-animate/angular-animate.js',
      'bower_components/angular-autocomplete/autocomplete.js',
      'bower_components/angular-slider/rzslider.min.js',
      'bower_components/angular-toastr/dist/angular-toastr.js',
      'bower_components/angular-bootstrap/ui-bootstrap.js',
      'bower_components/angular-mocks/angular-mocks.js',
      'app/*.js',
      'app/components/**/*.js',
      'app/components/*.js',
      'app/view*/**/*.js',
      'test/app/*.js',
      'test/app/view*/**/*.js',
      'test/app/components/**/*.js',
      'test/app/authentication/**/*.js'
    ],

    autoWatch : true,

    frameworks: ['jasmine'],

    browsers : ['Chrome'],

    plugins : [
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-jasmine',
            'karma-junit-reporter'
            ],

    junitReporter : {
      outputFile: 'test_out/unit.xml',
      suite: 'unit'
    }

  });
};
