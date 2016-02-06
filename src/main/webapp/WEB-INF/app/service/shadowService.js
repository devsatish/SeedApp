ascension.service("ShadowService", [ '$q', function($q) {
	
	var shadows = {};
	
	return {
		listen: function(target, value) {
			if(typeof shadows[target] == 'undefined') {
				shadows[target] = {
					'value': value,
					'defer': $q.defer()
				}
			}
			else {
				shadows[target].value = value;
			}
			return shadows[target].defer.promise;
		},
		reset: function(target) {
			if(typeof shadows[target] == 'undefined') {
				shadows[target] = {
					'value': '',
					'defer': $q.defer()
				}
			}
			shadows[target].defer.notify(target);
		},
		get: function(target) {
			if(typeof shadows[target] == 'undefined') {
				shadows[target] = {
					'value': '',
					'defer': $q.defer()
				}
			}
			return shadows[target].value;
		}		
	}
	
}]);

