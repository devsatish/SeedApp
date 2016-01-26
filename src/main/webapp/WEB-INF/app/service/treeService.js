ascension.service('TreeService', [ function() {
	
	if(typeof sessionStorage.expanded == 'undefined') {
		sessionStorage.expanded = angular.toJson([]);
	}
	
	var identity = function(branch) {
		return (branch.community ? branch.community : 'root') + '-' + branch.name + '-' + branch.id;
	};
	
	var toggle = function(branch) {
		branch.expanded = !branch.expanded;
		var expanded = angular.fromJson(sessionStorage.expanded);
		if(branch.expanded) {
			expanded.push(identity(branch));
		}
		else {
			expanded.splice(expanded.indexOf(identity(branch)), 1);
		}
		sessionStorage.expanded = angular.toJson(expanded);
	}
	
	var set = function(branch, expand) {		
		var expanded = angular.fromJson(sessionStorage.expanded);
		if(expand) {
			expanded.push(identity(branch));
		}
		else {
			expanded.splice(expanded.indexOf(identity(branch)), 1);
		}
		sessionStorage.expanded = angular.toJson(expanded);
		branch.expanded = expand;
	}

	var setAll = function(branch, expand) {
		set(branch, expand);
		if(branch.communities && branch.communities.length > 0) {	
			for(var i in branch.communities) {		
				setAll(branch.communities[i], expand);
			}
		}
	};
	
	return {
		toggle: function(branch) {
			toggle(branch);
		},
		expandAll: function(branches) {
			for(var i in branches) {
				setAll(branches[i], true);
			}
		},
		collapseAll: function(branches) {
			for(var i in branches) {
				setAll(branches[i], false);
			}
		},
		isExpanded: function(branch) {
			return angular.fromJson(sessionStorage.expanded).indexOf(identity(branch)) > -1;
		}
	}

}]);
