ascension.controller('CommunityController', ['$controller', '$routeParams', '$scope', 'CommunityService', function($controller, $routeParams, $scope, CommunityService) {

	angular.extend(this, $controller('HomeController', {$scope: $scope}));
		
	$scope.context = 'community';
	
	var setCommunity = function() {
		var community = CommunityService.findCommunityById($routeParams.communityId);
		$scope.title = community.name;
		$scope.index = community.depth;
		$scope.community = {};
		$scope.community[community.depth] = community;
	};
		
	if(CommunityService.ready()) {
		setCommunity();
	}
	else {
		CommunityService.after(setCommunity);
	}
	
}]);
