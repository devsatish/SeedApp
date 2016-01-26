module.exports = function(config){
    config.set({

        basePath : 'src/main/webapp/WEB-INF',

        files : [
            'resources/bower_components/jquery/dist/jquery.js',
            'resources/bower_components/angular/angular.js',
            'resources/bower_components/angular-mocks/angular-mocks.js',
            'resources/bower_components/angular-route/angular-route.js',
            
            'app/main.js',
            
            'app/config.js',
            
            'app/run.js',
            
            'app/controller/**/*.js',

            'app/directive/**/*.js',
            
            'app/service/**/*.js',            
            
            'app/factory/**/*.js',            
                        
        ],

        autoWatch : true,

        frameworks: ['jasmine'],

        browsers : ['Firefox', 'Chrome'],

        plugins : [
            'karma-jasmine',
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-junit-reporter'
            ],

        junitReporter : {
            outputFile: 'test_out/unit.xml',
            suite: 'unit'
        }

    });
};
