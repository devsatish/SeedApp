seedapp.directive('username', [ 'ModelService', function(ModelService) {
	return {
		restrict: 'E',
		scope: {},
		template: '<span>{{user.username}}</span>',
		controller: function($scope) {
			$scope.user = ModelService.get('user');
			console.log($scope.user);
		},
	};	
} ]);
