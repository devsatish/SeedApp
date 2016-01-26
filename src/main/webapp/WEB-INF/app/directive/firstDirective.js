ascension.directive('ascFirst', [  function() {	
	return {		
		link: function($scope, element, attr) {
			if(attr.ascFirst == 0) {
				element[0].focus();
			}
		}
	}
}]);