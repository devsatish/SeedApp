ascension.controller('AbstractController', ['$location', '$mdDialog', '$mdMedia', '$mdSidenav', '$scope', '$window', 'AuthService', 'FormDataService', function($location, $mdDialog, $mdMedia, $mdSidenav, $scope, $window, AuthService, FormDataService) {
	
	$scope.isAnonymous = function() {
		return AuthService.getRole() == 'ROLE_ANONYMOUS';
	};
	
	$scope.isUser = function() {
		return AuthService.getRole() == 'ROLE_USER';
	};
	
	$scope.isAdmin = function() {
		return AuthService.getRole() == 'ROLE_ADMIN';
	};
	
	$scope.showDialog = function(dialog, flex, clickAwayClose, escapeClose) {		
		$mdDialog.show({
			templateUrl: 'view/dialog.html',
			disableParentScroll: true,
			preserveScope: true,
			focusOnOpen: true,
			clickOutsideToClose: typeof clickAwayClose != 'undefined' ? clickAwayClose : true,
			escapeToClose: typeof escapeClose != 'undefined' ? escapeClose : true,
			fullscreen: $window.innerWidth < 500,
			scope: $scope,
			controller: function($scope) {
				$scope.dialog = 'view/dialogs/' + dialog + '.html';
				$scope.flex = typeof flex != 'undefined' ? flex : '25';
				
				$scope.cancel = function() {
					$mdDialog.cancel();
				};

				$scope.hide = function() {
					$mdDialog.hide();
				};
				
			}
		})
		.then(function() {
			console.log('Submitted ' + dialog);
			delete $scope.error;
		}, function() {
			console.log('Cancelled ' + dialog);
			
			if($location.search().back) {
				console.log('back');
				console.log($location.search().back);
				
				$location.path($location.search().back);
				$location.search('forward', null);
				$location.search('back', null);
			}
			
			// don't like having to do this
			angular.element('body').css('opacity', '');
			
			delete $scope.error;
			
	    });
	};
	
	$scope.openSideNav = function(id) {
		$mdSidenav(id).toggle().then(function () {
			console.log(id + ' open')
		});
	};
	
	$scope.closeSideNav = function(id) {
		$mdSidenav(id).close().then(function () {
			console.log(id + ' closed')
		});
	};
		
}]);
