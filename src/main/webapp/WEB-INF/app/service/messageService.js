ascension.service('MessageService', [ '$mdToast', 'AlertService', function($mdToast, AlertService) {
	
	return {
		info: function(message, delay) {
			AlertService.add({ 'type': 'INFO', 
							   'message': message 
							 }, "foo/bar");
//			$mdToast.show({
//				template: '<md-toast class="md-toast info">' + message + '</md-toast>',	        	
//				hideDelay: delay
//			});
		},
		success: function(message, delay) {
			AlertService.add({ 'type': 'SUCCESS', 
							   'message': message 
							 }, "foo/bar");
//			$mdToast.show({
//				template: '<md-toast class="md-toast success">' + message + '</md-toast>',
//	        	hideDelay: delay,
//			});
		},
		warning: function(message, delay) {
			AlertService.add({ 'type': 'WARNING', 
							   'message': message 
							 }, "foo/bar");
//			$mdToast.show({
//				template: '<md-toast class="md-toast warning">' + message + '</md-toast>',
//	        	hideDelay: delay,
//			});
		},
		error: function(message, delay) {
			AlertService.add({ 'type': 'ERROR', 
							   'message': message 
							 }, "foo/bar");
//			$mdToast.show({
//				template: '<md-toast class="md-toast error">' + message + '</md-toast>',
//	        	hideDelay: delay,
//			});
		}
	}
	
}]);
