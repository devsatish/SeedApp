/**
* @ngdoc directive
* @name  core.directive:alerts
* @restrict 'E'
* @requires core.service:AlertService
* @requires ng.$controller
* @requires ng.$rootScope
* @requires ng.$timeout
*
* @example
* <pre>
* 	<alerts types="WARNING, ERROR"><ul class="alertList list-unstyled"><!-- ngRepeat: alert in alerts --></ul></alerts>
* </pre>
* 
* @description 
* The alerts element directive provides alert messages based on the alert types in your application. Extends {@link core.controller:AbstractController 'AbstractController'}
* 
*/
ascension.directive('alerts', function (AlertService, $controller, $rootScope, $timeout) {
	return {
		template: '<span ng-if="alerts.length > 0" ng-repeat="alert in alerts"><span ng-include src="view"></span>',
		restrict: 'E',
		replace: false,
		scope: {},
		link: function ($scope, element, attr) {

			angular.extend(this, $controller('AbstractController', {$scope: $scope}));

		    /**
		     * @ngdoc property
		     * @name core.directive:alerts#fixed
		     * @propertyOf core.directive:alerts
		     *
		     * @description
		     * A variable to store the boolean result if the element 'fixed' is present in the attr object
		     */
			var fixed = typeof attr.fixed != 'undefined';
			
			/**
		     * @ngdoc property
		     * @name core.directive:alerts#duration
		     * @propertyOf core.directive:alerts
		     *
		     * @description
		     * A variable to store the duration value either from the 'attr' object or the config alerts 'duration'
		     */
			var duration = attr.seconds ? parseInt(attr.seconds) * 1000: config.alerts.duration;
			
			/**
		     * @ngdoc property
		     * @name core.directive:alerts#exclusive
		     * @propertyOf core.directive:alerts
		     *
		     * @description
		     * A boolean which determines if this alert will be the only one to recieve messages on the specified channel
		     */	
			var exclusive = typeof attr.exclusive != 'undefined';
			
			/**
		     * @ngdoc property
		     * @name core.directive:alerts#types
		     * @propertyOf core.directive:alerts
		     *
		     * @description
		     * This 'types' array variabble stores the type of alert messages from the 'attr' object 'types' property.
		     */
			var types = [];

			/**
		     * @ngdoc property
		     * @name core.directive:alerts#channels
		     * @propertyOf core.directive:alerts
		     *
		     * @description
		     * This 'channel' array variabble stores the type of alert messages from the 'attr' object 'channels' property.
		     */
			var channels = [];
			
			if(attr.channels) {
				var splitChannels = attr.channels.split(',');
				for(var i in splitChannels) {
					var channel = splitChannels[i].trim();
					channels.push(channel);
					AlertService.create(channel, exclusive);
				}
			}

			if(attr.types) {
				var splitTypes = attr.types.split(',');
				for(var i in splitTypes) {
					types.push(splitTypes[i].trim());
				}
			}

			if(types.length == 0) {
				types.push("ERROR");
			}
			
			/**
		     * @ngdoc property
		     * @name core.directive:alerts#facets
		     * @propertyOf core.directive:alerts
		     *
		     * @description
		     * An array variable to store the 'types' and 'channel' attributes from the 'attr' object
		     */			
			var facets = [];
			
			facets = facets.concat(types ? types : []);
			facets = facets.concat(channels ? channels : []);
			
			/**
		     * @ngdoc property
		     * @name core.directive:alerts#timers
		     * @propertyOf core.directive:alerts
		     *
		     * @description
		     * An object variable to store current timers
		     */		
			var timers = {};

			/**
			 * @ngdoc property
			 * @name core.directive:alerts#$scope.view
			 * @propertyOf core.directive:alerts
			 *
			 * @description
			 * 	A variable to store the 'attr' object 'view' property if present else it points to the 'default' view
			 */			
			$scope.view = attr.view ? attr.view : "view/alerts/default.html";

			/**
			 * @ngdoc property
			 * @name core.directive:alerts#$scope.alerts
			 * @propertyOf core.directive:alerts
			 *
			 * @description
			 * 	An array variable to store the alert messages
			 */
			$scope.alerts = [];

			/**
			 * @ngdoc method
			 * @name core.directive:alerts#$scope.remove
			 * @methodOf core.directive:alerts
			 * @param {Alert} alert an alert object
			 * @returns {void} returns void
			 * 
			 * @description
			 * 	This method uses the $timeout service to invoke the 'AlertService' to remove 'alert' 'after the 'duration' provided.
			 */
			$scope.remove = function(alert) {
				$timeout(function() {
					AlertService.remove(alert);
				}, duration);
			};
			
			/**
			 * @ngdoc method
			 * @name core.directive:alerts#alertIndex
			 * @methodOf core.directive:alerts
			 * @param {Alert} id an alert object
			 * @returns {void} returns void
			 * 
			 * @description
			 * 	A method to return specific 'alert' based on the 'id' passed on the $scope.alerts object
			 */	
			var alertIndex = function(id) {
				for(var i in $scope.alerts) {
					if($scope.alerts[i].id == id) {
						return i;
					}
				}
			};

			/**
			 * @ngdoc method
			 * @name core.directive:alerts#handle
			 * @methodOf core.directive:alerts
			 * @param {Alert} alert an Alert object
			 * @returns {void} returns void
			 * 
			 * @description
			 * 	A method to remove 'alert' from a view.
			 */	
			var handle = function(alert) {
				if(alert.remove) {
					alert.fade = true;
					$timeout(function() {
						$scope.alerts.splice(alertIndex(alert.id), 1);
					}, 350);
				}
				else {
					if(types.indexOf(alert.type) > -1) {
						$scope.alerts[$scope.alerts.length] = alert;					
						if(!fixed) {
							if(alert.type != "ERROR") {							
								$scope.remove(alert);
							}
						}
					}
				}
			};

			for(var i in facets) {
				if(channels.length > 0 && types.indexOf(facets[i]) > -1) continue;
				var alerts = AlertService.get(facets[i]);
				if(typeof alerts == 'undefined') break;
				if(alerts.defer) {
					for(var j in alerts.list) {
						handle(alerts.list[j]);
					}
					alerts.defer.promise.then(function(alert){ 
							// resolved
						}, function(alert) { 
							// rejected
						}, function(alert) { 
							// notified
							handle(alert);
					});
				}
			}

			$rootScope.$on("$routeChangeStart", function(event, next, current) {				
    			// remove alerts on route change
    			for(var i in $scope.alerts) {
    				AlertService.remove($scope.alerts[i]);
    			}
			});
			
	    }
	};
	
});
