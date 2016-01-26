ascension.directive('ascBreadcrumbs', [ 'BreadcrumbService', function(BreadcrumbService) {
 	return {
 		restrict: 'E',
    	scope: {},
    	template: 	'<md-content class="breadcrumbs">' +
			    		'<span><a href="home">Home</a><span>' +
			    		'<span ng-repeat="breadcrumb in breadcrumbs"> / <a href="{{breadcrumb.view}}">{{breadcrumb.display}}</a><span>' +
					'</md-content>',
		link: function($scope, element, attr) {
			BreadcrumbService.listen().then(null, null, function(data) {
				$scope.breadcrumbs = data;
				sessionStorage.breadcrumbs = angular.toJson(data);
			});
		},
		controller: function($scope) {
			if(typeof sessionStorage.breadcrumbs != 'undefined' && sessionStorage.breadcrumbs.length > 0) {
				var data = angular.fromJson(sessionStorage.breadcrumbs);
			}
		}
    }
}]);
