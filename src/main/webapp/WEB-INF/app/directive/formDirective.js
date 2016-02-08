seedapp.directive('ascForm', [ 'AlertService', 'ModelService', 'ShadowService', function(AlertService, ModelService, ShadowService) {
	return {
		restrict: 'E',
		replace: false,
		scope: {
			model: '@',
			property: '@',
			action: '@',
			context: '=',
			object: '=?',
			cancel: '='
		},		
		template: 	'<form name="form" novalidate ng-submit="submit(action)">' +
						'<md-card-content>' +
							'<md-list>' +
								'<asc-input object="object" form="form" model="{{model}}" property="{{property}}"></asc-input>' +
							'</md-list>' +
							'<div layout="row" layout-align="bottom left" style="color: red" ng-messages="error">{{error}}</div>' +		
						'</md-card-content>' +
						'<md-card-actions ng-if="action == \'update\'" layout="row" layout-align="end center">' +
							'<md-button class="md-raised md-primary" type="submit" data-ng-disabled="!change || form.$invalid">Update</md-button>' +
							'<md-button class="md-raised md-warn" data-ng-disabled="!change" ng-click="reset()">Reset</md-button>' +
						'</md-card-actions>' +
						'<md-card-actions ng-if="action == \'create\'" layout="row" layout-align="end center">' +
							'<md-button class="md-raised md-primary" type="submit" data-ng-disabled="!change || form.$invalid">Create</md-button>' +
							'<md-button class="md-raised md-warn" ng-click="clear()">Cancel</md-button>' +
						'</md-card-actions>' +
					'</form>',
		link: {
			pre: function ($scope, element, attr) {
				if(typeof $scope.object == 'undefined') {
					$scope.object = {};
				}
			}
		},
		controller: function($scope) {
			
			$scope.submit = function(action) {
				$scope[action]();
			};
			
			$scope.create = function() {
				ModelService.create($scope.model, $scope.object, function(response) {					
					$scope.clear();
					AlertService.add({'type':'SUCCESS','message':$scope.model + ' was succefully created'});				
				}, function(error) {
					$scope.error = error;										
				});
			};
			
			$scope.clear = function() {				
				for(var key in $scope.data) {
					delete $scope.data[key].satisfied;
				}
				delete $scope.error;
				$scope.object = {};
				$scope.cancel();
			};
			
			$scope.update = function() {
				if($scope.valid) {
					var data = {};
					data[$scope.property] = $scope.object[$scope.property];
					ModelService.update($scope.model, data, function(response) {						
						AlertService.add({'type':'SUCCESS','message':$scope.property + ' of ' + $scope.model + ' has been successfully updated'});
						$scope.change = false;
					}, function(error) {
						console.log(error);
						$scope.error = error;
						$scope.reset();
					});					
				}
			};
			
			$scope.reset = function() {
				for(var key in $scope.object[$scope.property]) {
					if(key.indexOf('$$') == -1) {
						ShadowService.reset(key);
					}
				}
				$scope.change = false;
				MessageService.info($scope.property + ' of ' + $scope. model + ' has been reset', 5000);
			};
			
		}
	};	
} ]);
