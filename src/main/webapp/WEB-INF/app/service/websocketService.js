ascension.service('WebsocketService', [ '$q', function($q) {
	
	var socket = {
		client: null,
		stomp: null,
		status: 'disconnected'
	};
		
	var queue = [];
	
	var subscriptions = {};
	
	return {
		connect: function() {
			return $q(function(resolve) {
				socket.status = 'connecting';
				socket.client = new SockJS(window.location.base+"/connect", null, {transports: config.sockJsTransports});			
				socket.stomp = Stomp.over(socket.client);
				if(!config.stompDebug) {
					socket.stomp.debug = null; 
				}
				socket.stomp.connect({}, function(response) {
					for(var i = queue.length - 1; i >= 0; i--) {
						var subscription = socket.stomp.subscribe(queue[i].channel, queue[i].callback);						
						subscriptions[subscription.id] = {
							'channel': queue[i].channel,
							'callback': queue[i].callback,
							'unsubscribe': subscription.unsubscribe
						};
						queue.splice(i, 1);
					}
					socket.status = 'connected';
					resolve();
				}, function(error) {
					console.log(error);
				});
			});	
		},
		disconnect: function() {
			return $q(function(resolve) {
				socket.stomp.disconnect(function() {
					socket.status = 'disconnected';
					resolve();
				});
			});		
		},
		subscribe: function(channel, callback) {
			if(socket.connected) {
				var subscription = socket.stomp.subscribe(channel, callback);
				subscriptions[subscription.id] = {
					'channel': channel,
					'callback': callback,
					'unsubscribe': subscription.unsubscribe
				};
			}
			else {
				queue.push({'channel':channel,'callback':callback});
			}			
		},
		request: function(method, url, headers, data, dataType, success, failure) {
			
		},
		connecting: function() {			
			return socket.status == 'connecting';
		},
		connected: function() {			
			return socket.status == 'connected';
		},
		disconnected: function() {			
			return socket.status == 'disconnected';
		},
		resubscribe: function() {
			for(var i in subscriptions) {
				socket.stomp.subscribe(subscriptions[i].channel, subscriptions[i].callback);
			}
		}
	}
	
}]);
