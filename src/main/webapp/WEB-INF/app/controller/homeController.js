ascension.controller('HomeController', ['$controller', '$mdToast', '$routeParams', '$scope', 'AlertService', function($controller,   $mdToast,   $routeParams,   $scope,   AlertService) {

	angular.extend(this, $controller('AbstractController', {$scope: $scope}));
	
	angular.extend(this, $controller('AuthController', {$scope: $scope}));
		
	$scope.title = 'Home';
	
	$scope.context = 'home';
	
	switch($routeParams.action) {
		case 'login': {
			$scope.showDialog('login', '30');
		} break;
		case 'registration': {
			AlertService.add({ 'type': 'SUCCESS', 'message': 'Registration complete. Please login.' }, "");
			$scope.showDialog('login', '30');			
		} break;
		default: {
			
		} break;
	}
	
}]);
