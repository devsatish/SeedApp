seedapp.controller('UserController', ['$controller', '$location', '$scope', 'ModelService', function($controller, $location, $scope, ModelService) {
	
	angular.extend(this, $controller('AbstractController', {$scope: $scope}));
		
	$scope.user = ModelService.get('user');
							
	var hashToSelected = function(hash) {
		switch(hash) {
			case 'settings': return 0;
			default: {
				$location.hash('settings');
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
