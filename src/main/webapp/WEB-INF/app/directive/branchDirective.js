ascension.directive('ascBranch', [ '$compile', 'TreeService', function($compile, TreeService) {			
 	return {
 		restrict: 'E',
	    transclude: true,
    	scope: {
    		branch: '='
    	},
    	template: 	'<md-list>' +
						'<div class="transclude" ng-transclude></div>' +
						'<md-list-item ng-repeat="community in branch.communities" ng-if="branch.expanded">' +
							'<asc-branch branch="community" depth="depth"><div ng-transclude></div></asc-tree>' +
						'</md-list-item>' +
						'<md-list-item ng-repeat="collection in branch.collections" ng-if="branch.expanded">' +
						'<asc-branch branch="collection" depth="depth"><div ng-transclude></div></asc-tree>' +
					'</md-list-item>' +
						'<md-divider ng-if="branch.depth == 1"></md-divider>' +
					'</md-list>',
		controller: function($scope, TreeService) {
			$scope.toggle = function(branch) {
				TreeService.toggle(branch);
			};
		},
    	compile: function(tElement, tAttr, transclude) {
    		var contents = tElement.contents().remove();
            var compiledContents;
            return function($scope, iElement, iAttr) {
                if(!compiledContents) {
                    compiledContents = $compile(contents, transclude);
                }
                compiledContents($scope, function(clone, $scope) {
                	if(TreeService.isExpanded($scope.branch)) {
                		$scope.branch['expanded'] = true;
                	}
                	else {
                		$scope.branch['expanded'] = false;
                	}
                	iElement.append(clone);
                });
            };
    	}
    }
 	
}]);
