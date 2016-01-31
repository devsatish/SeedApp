ascension.controller('AuthController', ['$controller', '$location', '$scope', 'AlertService', 'AuthService', 'HttpService', 'MessageService', 'ModelService', function($controller, $location, $scope, AlertService, AuthService, HttpService, MessageService, ModelService) {
	
	angular.extend(this, $controller('AbstractController', {$scope: $scope}));
	
	$scope.registration = function(username, password, confirm, firstName, lastName, email) {
		$scope.registering = true;
		HttpService.request('POST', 'registration/begin', { 'Accept': 'application/text' }, {'username': username, 'password': password, 'confirm': confirm, 'firstName': firstName, 'lastName': lastName, 'email': email}, 'json', function(response) {			
			$scope.hide();			
			$scope.registering = false;
			if(response == 'SUCCESS') {
				MessageService.success('An email has been sent to ' + email + '.\nPlease confirm email to complete registration.', 5000);
			}
		}, function(error) {
			$scope.registering = false;
			$scope.error = error;
		});		
	};
	
	$scope.authenticate = function(username, password) {				
		HttpService.request('POST', 'login', { 'Content-Type':'application/x-www-form-urlencoded' }, 'username=' + username + '&password=' + password, 'text', function(response) {						
			$scope.hide();			
			AuthService.setRole(response.role);			
			angular.extend(sessionStorage, response);			
			ModelService.get('user');			
			ModelService.after('user', function() {
				ModelService.reconnect();
				if($location.search().forward) {
					$location.path($location.search().forward);
					$location.search('forward', null);
					$location.search('back', null);
				}
				else {
					$location.path('home');					
				}
				// don't like having to do this
				angular.element('body').css('opacity', '');
			});		
		}, function(error) {
			$scope.error = error;
		});
	};
	
	$scope.logout = function() {
		HttpService.request('POST', 'logout', { 'Accept': 'application/text' }, {}, null, function() {			
			delete sessionStorage.user;
			delete sessionStorage.role;
			delete sessionStorage.token;
			ModelService.reconnect();
			ModelService.reset('user');
			$location.path("/home");
		}, function(error) {
			$scope.error = error;
		});	
	};
	
}]);
