var config = {
	'sockJsTransports': ['websocket', 'xhr-streaming', 'xhr-polling', 'jsonp-polling'],
	'privateModels': ['user'],
	'stompDebug': false,
	'alerts': {
		'types': [
			'SUCCESS',
			'WARNING',
			'ERROR',
			'INFO',
			'UNKNOWN'
		],
		'classes': {
			'SUCCESS': 'success',
			'WARNING': 'warning',
			'ERROR':   'error',
			'UNKNOWN': 'error',
			'INFO': 'info',
			'DEFAULT': 'info'
		},
		'duration': 15000,
		'flush': 900000,
		'email': 'wwelling@outlook.com'
	}
}

ascension.config(['$locationProvider', '$mdThemingProvider', '$routeProvider', function($locationProvider, $mdThemingProvider, $routeProvider) {
	
	$locationProvider.html5Mode({
		enabled: true,
		requireBase: false
	});

	$routeProvider.
		when('/403', {
			templateUrl: 'view/403.html'
		}).
		when('/admin/view', {
			templateUrl: 'view/admin.html',
			controller: 'AdminController',
			access: ['ROLE_ADMIN']
		}).
		when('/user/view', {
			templateUrl: 'view/user.html',
			controller: 'UserController',
			access: ['ROLE_USER', 'ROLE_ADMIN'],
			reloadOnSearch: false
		}).
		when('/community/:communityId', {
			templateUrl: 'view/community.html',
			controller: 'CommunityController'
		}).
		when('/collection/:collectionId', {
			templateUrl: 'view/collection.html',
			controller: 'CollectionController'
		}).
		when('/home/:action', {
			templateUrl: 'view/home.html',
			controller: 'HomeController'
		}).
		when('/:action/:token', {
			templateUrl: 'view/home.html',
			controller: 'HomeController'
		}).
		otherwise({
			redirectTo: '/home',
			templateUrl: 'view/home.html',
			controller: 'HomeController'
		});
}]).run(['$location', '$rootScope', 'AuthService', 'BreadcrumbService', function($location, $rootScope, AuthService, BreadcrumbService) {
	
	angular.element("body").fadeIn(1500);
	 
	$rootScope.$on("$routeChangeSuccess", function(event, next, current) {		
		if(typeof next.$$route != 'undefined') {
			if(BreadcrumbService.ready()) {
				BreadcrumbService.process(next);
			} else {
				BreadcrumbService.init().then(function() {
					BreadcrumbService.process(next);
				});
			}
		}
		else {
			BreadcrumbService.clear();
		}
	});
	
	$rootScope.$on("$routeChangeStart", function(event, next, current) {
	    if(next.access) {
	    	var role = AuthService.getRole();
	    	if(next.access.indexOf(role) < 0) {
	    		if(role == 'ROLE_ANONYMOUS') {
		    		event.preventDefault();
		        	$location.path("/home/login").search({'forward': next.$$route.originalPath, 'back': 'home'});
		    	}
		    	else {
		        	event.preventDefault();
		        	$location.path("/403");
		        }
	    	}
	    }
	});
		
}]);
