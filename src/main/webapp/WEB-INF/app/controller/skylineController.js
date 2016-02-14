seedapp.controller('SkylineController', ['$controller', '$scope', 'ModelService', function($controller, $scope, ModelService) {
	
	angular.extend(this, $controller('AbstractController', {$scope: $scope}));
		
	$scope.skyline = ModelService.get('skyline', function(tuple) {
		console.log(tuple);
		$scope.skyline[tuple.timeStampId] = tuple;
		$scope.$apply();
	});
	
	console.log($scope.skyline)
		
}]);
