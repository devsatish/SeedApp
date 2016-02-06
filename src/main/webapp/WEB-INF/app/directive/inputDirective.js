ascension.directive('ascInput', ['CommunityService', 'FormDataService', 'ModelService', function(CommunityService, FormDataService, ModelService) {
	return {
		restrict: 'E',
		replace: false,
		require: '^form',
		scope: false,
		template: 	'<md-input-container class="md-block" ng-repeat="(key, value) in data" ng-if="value.satisfied || satisfyPrerequisites(value)">' +						
						'<md-label ng-if="value.type != \'boolean\' && value.type != \'hidden\'">{{value.gloss}}</md-label>' +
						
						'<input asc-first="{{$index}}" ng-if="!value.self" name="{{key}}" property="{{property}}" key="{{key}}" type="{{value.type}}" ng-change="processChange(key)" ng-model="object[property][key]" ng-required="{{value.required}}" ng-minlength="{{value.validations.minlength}}" ng-maxlength="{{value.validations.maxlength}}" ng-pattern="{{value.validations.pattern}}" ng-trim="true"  ng-model-options="{allowInvalid: true}" shadow="model" aria-label="{{value.gloss}}">' +
												
						'<input asc-first="{{$index}}" ng-if="value.self && value.type == \'text\'" name="{{key}}" type="{{value.type}}" ng-change="processChange(key)" ng-model="object[key]" ng-required="{{value.required}}" ng-minlength="{{value.validations.minlength}}" ng-maxlength="{{value.validations.maxlength}}" ng-pattern="{{value.validations.pattern}}" ng-trim="true" ng-model-options="{allowInvalid: true}" aria-label="{{value.gloss}}">' +
						
						'<input ng-if="value.self && value.type == \'hidden\'" name="{{key}}" type="{{value.type}}" ng-init="object[key]=context.id" ng-model="object[key]" ng-value="context.id" aria-label="{{value.gloss}}">' +
						
						'<md-checkbox ng-if="value.self && value.type == \'boolean\'" ng-model="object[key]" aria-label="{{value.gloss}}">{{value.gloss}}</md-checkbox>' +
						
						'<md-select ng-if="value.self && value.type == \'select\' && value.ready" ng-model="object[key]" ng-required="{{value.required}}" ng-change="onChangeSelect(key, value)" aria-label="{{value.gloss}}">' +
				        	'<md-option ng-repeat="item in value.select" value="{{getValue(item)}}">{{getDisplay(item)}}</md-option>' +
				        '</md-select>' +
				      
						'<div ng-messages="form[key].$error">' +
							'<div ng-message="required">{{value.gloss}} is required.</div>' +
							'<div ng-message="email">{{value.gloss}} is not valid.</div>' +
							'<div ng-message="pattern">{{value.gloss}} is not a valid 10 digit phone number</div>' + 
							'<div ng-message="minlength">{{value.gloss}} must be at least {{value.validations.minlength}} characters.</div>' +
							'<div ng-message="maxlength">{{value.gloss}} must be equal to or less than {{value.validations.maxlength}} characters.</div>' +
						'</div>' +
					'</md-input-container>',		
		link: function ($scope, element, attr, form) {

			FormDataService.get($scope.model, $scope.property).then(function(data) {
				$scope.data = data;
				for(var key in $scope.data) {
					if($scope.data[key]['type'] == 'select') {
						if($scope.data[key]['entity']) {
							$scope.data[key].ready = false;
							if(!FormDataService.promised($scope.model, key)) {
								(function($scope, key) {
									$scope.data[key].defer.promise.then(null, null, function(select) {
										$scope.data[key]['select'] = select;
										$scope.data[key].ready = true;
									});
								})($scope, key);
							}
						}
						else {
							$scope.data[key].ready = true;
						}
					}
				}
			});
			
			$scope.form = form;
			
		},
		controller: function($scope) {
			
			$scope.valid;
			
			$scope.change = false;
			
			$scope.getValue = function(item) {
				return item.id ? item.id : item.name ? item.name : item;
			};
			
			$scope.getDisplay = function(item) {
				return item.name ? item.name : item;
			};
				
			$scope.processChange = function(key) {				
				$scope.change = true;
				if($scope.form[key].$valid) {
					$scope.valid = true;
				}
				else {
					$scope.valid = false;
					if($scope.form[key].$error['required']) {			
						
					}
					if($scope.form[key].$error['minlength']) {			
						
					}
					if($scope.form[key].$error['maxlength']) {			
						
					}
					if($scope.form[key].$error['email']) {			
						
					}
					if($scope.form[key].$error['pattern']) {			
						
					}
				}
			};
			
			$scope.onChangeSelect = function(key, value) {
				for(var i in $scope.data) {
					if($scope.data[i].prerequisites != null) {						
						for(var j in $scope.data[i].prerequisites) {							
							if($scope.data[i].prerequisites[j] == key) {
								delete $scope.object[i];
								$scope.data[i].satisfied = false;								
								$scope.data[i].filters[key].value = j;
								FormDataService.get($scope.model, $scope.property);
							}
						}
					}
				}
			};
			
			$scope.satisfyPrerequisites = function(value) {				
				var prerequisiteKeys = value.prerequisites;
				if(!prerequisiteKeys) {
					return true;
				}
				else {
					var prerequisites = {};
					var satisfied = true;
					for(var i in prerequisiteKeys) {
						if(typeof $scope.object[prerequisiteKeys[i]] == 'undefined') {							 
							satisfied = false;
						}
						else {
							prerequisites[prerequisiteKeys[i]] = $scope.object[prerequisiteKeys[i]];
							var list =  ModelService.get(prerequisiteKeys[i] + '/all');
							for(var j in list) {
								if(list[j].name == prerequisites[prerequisiteKeys[i]]) {
									value.filters[prerequisiteKeys[i]].value = list[j].id.toString();
								}
							}
						}
					}
					if(satisfied) {
						value.satisfied = true;
						FormDataService.get($scope.model, $scope.property);
					}
					return satisfied;
				}
			}
						
		}
	};	
} ]);
