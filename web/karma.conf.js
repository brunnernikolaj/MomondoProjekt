module.exports = function(config){
  config.set({

    basePath : './',
     
     reporters : ['coverage', 'progress'],
     
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
      'test/app/components/**/*.js'
    ],
    
    preprocessors : {
        'app/components/*.js': ['coverage']
    },
    
    autoWatch : true,
    
    
    
    frameworks: ['jasmine'],

    browsers : ['Chrome'],

    plugins : [
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-jasmine',
            'karma-junit-reporter',
            'karma-coverage'
            ],

    junitReporter : {
      outputFile: 'test_out/unit.xml',
      suite: 'unit'
    },
    
    coverageReporter: {
        type : 'html',
        dir : 'coverage/'
      }

  });
};
