ascension.controller('UserController', ['$controller', '$location', '$scope', 'ModelService', function($controller, $location, $scope, ModelService) {
	
	angular.extend(this, $controller('AbstractController', {$scope: $scope}));
		
	$scope.user = ModelService.get('user');
							
	var hashToSelected = function(hash) {
		switch(hash) {
			case 'profile': return 0;
			case 'events': return 1;
			case 'assets': return 2;
			case 'exhibits': return 3;
			case 'themes': return 4;
			case 'settings': return 5;
			default: {
				$location.hash('profile');
				return 0;
			}
		}
	};
	
	$scope.selected = hashToSelected($location.hash());
	
	$scope.select = function(tab) {		
		$scope.selected = hashToSelected(tab);
		$location.hash(tab);
	};
	
}]);
