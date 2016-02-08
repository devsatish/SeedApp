seedapp.directive('shadow', [ 'ModelService', 'ShadowService', function(ModelService, ShadowService) {
 	return {
    	scope: {
    		ngModel: '=',
    		shadow: '=',    		
    		property: '@',
    		key: '@'
    	},
    	link: function($scope, element, attr) {
    		
    		var model = {};
    		
    		model['$$' + $scope.key] = $scope.ngModel;
    		    		
    		ModelService.promise($scope.shadow).then(function(data) {
    			// resolve
    		}, function(data) {
    			// reject
    		}, function(data) {
    			if(typeof $scope.property == 'undefined') {
    				model['$$' + $scope.key] = data[$scope.key];
    			}
    			else {
    				model['$$' + $scope.key] = data[$scope.property][$scope.key];
    			}
    		})

    		ShadowService.listen($scope.key, model['$$' + $scope.key]).then(function() {}, function() {}, function(target) {    			
    			$scope.ngModel = model['$$' + target];
    		});
    	}
    }
}]);
