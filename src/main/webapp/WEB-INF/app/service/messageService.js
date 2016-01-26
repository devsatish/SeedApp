ascension.service('MessageService', [ '$mdToast', function($mdToast) {
	
	return {
		info: function(message, delay) {			
			$mdToast.show({
				template: '<md-toast class="md-toast info">' + message + '</md-toast>',	        	
				hideDelay: delay
			});
		},
		success: function(message, delay) {			
			$mdToast.show({
				template: '<md-toast class="md-toast success">' + message + '</md-toast>',
	        	hideDelay: delay,
			});
		},
		warning: function(message, delay) {			
			$mdToast.show({
				template: '<md-toast class="md-toast warning">' + message + '</md-toast>',
	        	hideDelay: delay,
			});
		},
		error: function(message, delay) {
			$mdToast.show({
				template: '<md-toast class="md-toast error">' + message + '</md-toast>',
	        	hideDelay: delay,
			});
		}
	}
	
}]);
