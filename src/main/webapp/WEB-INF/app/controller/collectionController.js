ascension.controller('CollectionController', ['$controller', '$routeParams', '$scope', 'CommunityService', function($controller, $routeParams, $scope, CommunityService) {

	angular.extend(this, $controller('HomeController', {$scope: $scope}));
	
	$scope.context = 'collection';
	
	var setCollection = function() {
		var collection = CommunityService.findCollectionById($routeParams.collectionId);
		$scope.title = collection.name;
		$scope.index = collection.depth;
		$scope.collection = {};
		$scope.collection[collection.depth] = collection;
	};
	
	if(CommunityService.ready()) {
		setCollection();
	}
	else {
		CommunityService.after(setCollection);
	}
	
}]);
