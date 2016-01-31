ascension.controller('HomeController', ['$controller', '$mdToast', '$routeParams', '$scope', 'AlertService', 'MessageService', 'CommunityService', function($controller, $mdToast, $routeParams, $scope, AlertService, MessageService, CommunityService) {

	angular.extend(this, $controller('AbstractController', {$scope: $scope}));
	
	angular.extend(this, $controller('AuthController', {$scope: $scope}));
	
	$scope.communities = CommunityService.get();
		
	$scope.title = 'Home';
	
	$scope.context = 'home';
		
	switch($routeParams.action) {
		case 'login': {
			$scope.showDialog('login', '30');
		} break;
		case 'registration': {	
			MessageService.success('Registration complete. Please login.', 5000);			
			$scope.showDialog('login', '30');			
		} break;
		default: {
			
		} break;
	}
	
}]);
