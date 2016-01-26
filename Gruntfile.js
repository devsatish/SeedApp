module.exports = function(grunt) {

	var build = {
		app: 'src/main/webapp/WEB-INF/app',
		view: 'src/main/webapp/WEB-INF/view',
		styles: 'src/main/webapp/WEB-INF/resources/styles'
	};

	grunt.initConfig({

		build: build,

		useminPrepare: {
			html: '<%= build.view %>/index.jsp',
			options: {
		        dest: '<%= build.view %>'
		    }
		},

		jshint: {
			options: {
				jshintrc: '.jshintrc',
				reporter: require('jshint-stylish')
			},
			all: [
			      	'Gruntfile.js',
			      	'<%= build.app %>/**/*.js',
			      ]
		},

		concat: {			
			options: {
				separator: ';'
			},			
			app: {
				src: [
				      	'<%= build.app %>/**/*.js',
				     ],
				dest: '<%= build.app %>/scripts/concat.js'
			}
		},

		uglify: {
			options: {
				mangle: false
			},
			app: {
				src:  '<%= build.app %>/scripts/concat.js',
				dest: '<%= build.app %>/scripts/concat.js'
			}
		},

		usemin: {
			html: '<%= build.view %>/index.jsp',
			options: {
				assetsDirs: ['<%= build.app %>/app']
			}
		},
		
		compass: {
			dist: {
				options: {
					sassDir: '<%= build.styles %>/sass',
					cssDir: '<%= build.styles %>'
				}
			}
		},
		watch: {
			css: {
				files: '**/*.scss',
				tasks: ['compass']
			}
		}

	});

	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-usemin');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-contrib-compass');
	grunt.loadNpmTasks('grunt-contrib-watch');

	grunt.registerTask('default', ['jshint', 'watch']);

	grunt.registerTask('develop', ['jshint', 'useminPrepare', 'concat', 'usemin', 'watch']);
	
	grunt.registerTask('deploy', ['jshint', 'useminPrepare', 'concat', 'uglify', 'usemin', 'compass']);

};
