seedapp.controller('SkylineController', ['$controller', '$scope', 'ModelService', function($controller, $scope, ModelService) {
	
	angular.extend(this, $controller('AbstractController', {$scope: $scope}));
		
	$scope.skyline = ModelService.get('skyline', function(data) {
		console.log(data);
		$scope.skyline.vectors.push(data);
	}, true);
	
	console.log($scope.skyline);
	
}]);
