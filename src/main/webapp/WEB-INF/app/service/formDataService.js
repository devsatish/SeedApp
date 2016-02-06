ascension.service("FormDataService", [ '$q', 'ModelService', function($q, ModelService) {
	
	var data = ModelService.get('data');
	
	var promises = {};
	
	ModelService.after('data', function() {		
		for(var model in data) {			
			for(var property in data[model]) {
				if(property != 'self') break;
				for(var key in data[model][property]) {
					data[model][property][key]['self'] = true;
					if(data[model][property][key]['entity']) {
						data[model][property][key]['defer'] = $q.defer();
					}
				}
			}
		}
	});
	
	var resolveForm = function(resolve, model, property) {
		if(typeof data[model][property] == 'undefined') {
			
			for(var key in data[model]['self']) {				
				if(data[model]['self'][key]['type'] == 'select' && data[model]['self'][key]['entity']) {					
					
					ModelService.post('entity/' + data[model]['self'][key]['entity'], {'properties': data[model]['self'][key]['facets'], 'filters': data[model]['self'][key]['filters']});
					
					if(typeof promises[model + '-' + key] == 'undefined') {
						(function(data, model, key) {
							promises[model + '-' + key] = ModelService.promise('entity/' + data[model]['self'][key]['entity']).then(null, null, function(cv) {
								data[model]['self'][key].defer.notify(cv);
							});
						})(data, model, key);
					}
					
				}
			}
			resolve(data[model]['self']);
		}
		else {
			resolve(data[model][property]);
		}
	};
		
	return {		
		get: function(model, property) {
			return $q(function(resolve) {
				if(typeof data[model] == 'undefined') {
					ModelService.after('data', function() {
						resolveForm(resolve, model, property);
					});
				}
				else {
					resolveForm(resolve, model, property);
				}
			});
		},
		update: function(model) {
			for(var key in data[model]['self']) {
				if(data[model]['self'][key]['type'] == 'select' && data[model]['self'][key]['entity']) {
					ModelService.post('entity/' + data[model]['self'][key]['entity'], {'properties': data[model]['self'][key]['facets'], 'filters': data[model]['self'][key]['filters']});
				}
			}
		},
		promised: function(model, key) {
			if(typeof promises[model + '-' + key].established == 'undefined') {
				promises[model + '-' + key]['established'] = true;
				return false;
			}
			return true;
		}
	}
	
}]);

