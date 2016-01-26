ascension.directive('ascTree', [ 'TreeService', function(TreeService) {	
 	return {
 		restrict: 'E',
 		transclude: true,
    	scope: {
    		root: '=',
    		context: '='
    	},
    	template: 	'<md-content ng-if="context != \'collection\'">' +
    					'<md-button data-ng-disabled="false" class="md-raised md-primary" ng-click="expandAll()">Expand All</md-button>' +
    					'<md-button data-ng-disabled="false" class="md-raised md-primary" ng-click="collapseAll()">Collapse All</md-button>' +
    				'</md-content>' +
    				'<md-content class="md-padding">' +
			    		'<div ng-repeat="community in root">' +
							'<asc-branch branch="community">' +
								'<ng-md-icon ng-if="(branch.communities.length > 0 || branch.collections.length > 0) && branch.expanded" class="clickable" icon="remove_circle_outline" ng-click="toggle(branch)"></ng-md-icon>' +
								'<ng-md-icon ng-if="(branch.communities.length > 0 || branch.collections.length > 0) && !branch.expanded" class="clickable" icon="add_circle_outline" ng-click="toggle(branch)"></ng-md-icon>' +
								'<span class="space" ng-class="{\'community\': branch.communities && branch.communities.length == 0 && branch.collections.length == 0, \'collection\': branch.assets}"></span>' +
								'<span class="transcluded">' +
									'<a ng-if="branch.communities" class="community" href="community/{{branch.id}}">{{branch.name}}</a>' +
									'<a ng-if="branch.assets" class="collection" href="collection/{{branch.id}}">{{branch.name}}</a>' +					
								'</span>' +
							'</asc-branch>' +
						'</div>' +
					'</md-content>',
		controller: function($scope) {
									
			$scope.expandAll = function() {
				TreeService.expandAll($scope.root);
			};
			
			$scope.collapseAll = function() {
				TreeService.collapseAll($scope.root);
			};

		}
    }
}]);
