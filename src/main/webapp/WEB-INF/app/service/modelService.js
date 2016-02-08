seedapp.service('ModelService', [ '$q', 'HttpService', 'WebsocketService', function($q, HttpService, WebsocketService) {
		
	var store = {};
	
	var prefix = window.location.base + '/';
	
	if(WebsocketService.disconnected()) {
		WebsocketService.connect();
	}
	
	var clean = function(object) {
		delete object.promise;
		delete object.notify;
		delete object.resolve;
		delete object.reject;
	};
	
	var create = function(model, subscription) {
		store[model] = {
			'object': {},
			'ready': false,
			'subscription': subscription,
			'defer': $q.defer()
		}
		angular.extend(store[model].object, store[model].defer);
	};
		
	var subscribe = function(model) {
		var privateModel = config.privateModels.indexOf(model) > -1;
		if(store[model].subscription || privateModel) {			
			var channel = privateModel ? '/private/queue/' + model : '/broadcast/' + model;
			WebsocketService.subscribe(channel, function(data) {
				var obj = angular.fromJson(data.body);				
				if(privateModel) {
					angular.extend(store[model].object, obj);					
					store[model].defer.notify(store[model].object);
				}
				else {
					store[model].subscription(obj);
				}
			});
		}
	};
		
	return {
		get: function(model, subscription, skipCache) {
			
			if(!store[model]) {
				create(model, subscription);
			}
			else {
				if(!skipCache) {
					return store[model].object;
				}	
			}
			
			store[model].defer.promise.then(function(data) {
				// resolve
				angular.extend(store[model].object, data);
				clean(store[model].object);
			}, function(data) { 
				// reject
				console.log(data);
			}, function(data) { 
				// notify
				angular.extend(store[model].object, data);
				clean(store[model].object);
			});
			
			HttpService.request('GET', prefix + model + '/get', {}, {}, null, function(response) {						
				store[model].defer.notify(response);
			}, function(error) {
				// handle model errors
			});
			
			subscribe(model);
			
			return store[model].object;
		},
		post: function(model, data) {
			
			if(!store[model]) {
				create(model);
			}
			
			store[model].defer.promise.then(function(data) {
				// resolve
				angular.extend(store[model].object, data);
				clean(store[model].object);
			}, function(data) { 
				// reject
				console.log(data);
			}, function(data) { 
				// notify
				angular.extend(store[model].object, data);
				clean(store[model].object);
			});
			
			HttpService.request('POST', prefix + model + '/get', {}, data, 'json', function(response) {						
				store[model].defer.notify(response);
			}, function(error) {
				// handle model errors
			});
			
			return store[model].object;
		},
		create: function(model, data, success, error) {
			
			if(!store[model]) create(model);
			
			HttpService.request('POST', prefix + model + '/create', { 'Content-Type':'application/json', 'Accept': 'application/json' }, data, 'json', success, error);
		},
		update: function(model, data, success, error) {
						
			if(!store[model]) create(model);
			
			HttpService.request('POST', prefix + model + '/update', { 'Content-Type':'application/json', 'Accept': 'application/text' }, data, 'json', success, error);
		},
		set: function(model, data) {
			
			if(!store[model]) create(model);
			
			store[model].defer.notify(data);
		},
		promise: function(model) {
			
			if(!store[model]) create(model);
			
			return store[model].defer.promise;
		},
		after: function(model, callback) {
			
			if(!store[model]) create(model);
			
			store[model].defer.promise.then(function(data) {
				// resolve
				callback();
				store[model].ready = true;
			}, function(data) { 
				// reject
				console.log(data);
			}, function(data) {
				// notify
				callback();
				store[model].ready = true;
			});			
		},
		ready: function(model) {
			
			if(!store[model]) create(model);
			
			return store[model].ready;
		},
		reset: function(model) {
			delete store[model];
		},
		reconnect: function() {
			if(WebsocketService.disconnected()) {
				WebsocketService.connect().then(function() {
					WebsocketService.resubscribe();
				});
			}
		}
	}
	
}]);
