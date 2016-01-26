ascension.service('BreadcrumbService', [ '$q', 'CommunityService', function($q, CommunityService) {

	var breadcrumbs = [];
	
	var defer = $q.defer();
	
	var init = $q.defer();
	
	var ready = false;
	
	var depth = 0;
	
	var indexOf = function(breadcrumb) {
		for(var i = 0; i < breadcrumbs.length; i++) {
			if(breadcrumb.view == breadcrumbs[i].view) {
				return i + 1;
			}
		}
		return 0;
	}
	
	var lengths = {
		'community': '/community'.length, 
		'collection': '/collection'.length
	}
	
	return {
		process: function(next) {
			
			var view = next.$$route.originalPath;
			
			for(var i in next.params) {
				view = view.replace(':' + i, next.params[i])
			}
			
			var display = 'Unknown';
			
			if(view.substring(1, lengths.community) == 'community') {	
				var community = CommunityService.findCommunityById(view.substring(lengths.community + 1));
				breadcrumbs = community.trail;
			}
			else if(view.substring(1, lengths.collection) == 'collection') {
				var collection = CommunityService.findCollectionById(view.substring(lengths.collection + 1));
				breadcrumbs = collection.trail;
			}
			else {
				if(false) {
					breadcrumb = {
						'view': view,
						'display': display
					}				
					var index = indexOf(breadcrumb);
					if(index == 0) {
						depth++;
						breadcrumbs.push(breadcrumb);
					}
					else {
						breadcrumbs.splice(index, breadcrumbs.length - index);
					}
				}
			}
						
			defer.notify(breadcrumbs);
		},
		pop: function() {
			depth--;
			breadcrumbs.pop();
			defer.notify(breadcrumbs);
		},
		set: function(breadcrumbs) {
			breadcrumbs = breadcrumbs;
			depth = breadcrumbs.length;
			defer.notify(breadcrumbs);
		},
		clear: function() {
			depth = 0;
			breadcrumbs = [];
			defer.notify(breadcrumbs);
		},
		get: function() {
			return breadcrumbs;
		},
		listen: function() {
			init.resolve();
			ready = true;
			return defer.promise;
		},
		init: function() {
			return init.promise;
		},
		ready: function() {
			return ready;
		}
		
	}
	
}]);
