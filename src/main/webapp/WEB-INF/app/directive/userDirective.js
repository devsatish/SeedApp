ascension.directive('fullname', [ 'ModelService', function(ModelService) {
	return {
		restrict: 'E',
		scope: {},
		template: '<span>{{user.profile.firstName}} {{user.profile.lastName}}</span>',
		controller: function($scope) {
			$scope.user = ModelService.get('user');
		},
	};	
} ]);
