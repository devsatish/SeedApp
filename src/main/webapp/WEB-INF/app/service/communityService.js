ascension.service('CommunityService', [ '$route', 'ModelService', function($route, ModelService) {
	
	var communities = ModelService.get('community/all', function(community) {
		var set = setCommunity(communities, community);
		if(!set) {
			community['depth'] = 1;
			communities[Object.keys(communities).length] = community;
		}
		$route.reload();
	});
	
	var setCommunity = function(branch, community) {
		var set = false;
		for(var i in branch) {
			if(branch[i].id == community.id) {
				community['depth'] = branch[i].depth;
				community['expanded'] = branch[i].expanded;
				branch[i] = community;
				return true;
			}
			if(branch[i].communities.length > 0) {
				set = setCommunity(branch[i].communities, community);
				if(set) {
					return set;
				}
			}
		}
		return set;
	};
	
	var setDepths = function(branch, depth) {
		depth++;
		for(var i in branch) {
			branch[i]['depth'] = depth;
			if(branch[i].collections.length > 0) {				
				for(var j in branch[i].collections) {
					branch[i].collections[j]['depth'] = depth + 1;
				}
			}
			if(branch[i].communities.length > 0) {				
				setDepths(branch[i].communities, depth);
			}
		}
	};
	
	var findCommunityById = function(branch, id, trail) {
		for(var i in branch) {
			if(branch[i].id == id) {
				if(typeof trail != 'undefined') {
					trail.push({'view': '/community/' + branch[i].id, 'display': branch[i].name});				
					branch[i]['trail'] = trail;
				}
				return branch[i];
			}
			if(branch[i].communities.length > 0) {
				if(typeof trail != 'undefined') {
					trail.push({'view': '/community/' + branch[i].id, 'display': branch[i].name});
				}
				var community = findCommunityById(branch[i].communities, id, trail);
				if(typeof community != 'undefined') {
					return community;
				}
				else {
					if(typeof trail != 'undefined') {
						trail.pop();
					}
				}
			}
		}
	};
	
	var findCollectionById = function(branch, id, trail) {
		for(var i in branch) {			
			for(var j in branch[i].collections) {
				if(branch[i].collections[j].id == id) {
					trail.push({'view': '/community/' + branch[i].id, 'display': branch[i].name});
					trail.push({'view': '/collection/' + branch[i].collections[j].id, 'display': branch[i].collections[j].name});
					branch[i].collections[j]['trail'] = trail;
					return branch[i].collections[j]; 
				}
			}
			if(branch[i].communities.length > 0) {
				trail.push({'view': '/community/' + communities[i].id, 'display': communities[i].name});
				var collection = findCollectionById(branch[i].communities, id, trail);
				if(typeof collection != 'undefined') {
					return collection;
				}
				else {
					trail.pop();
				}
			}
		}
	};
	
	if(ModelService.ready('community/all')) {
		setDepths(communities, 0);
	}
	else {
		ModelService.after('community/all', function() {
			setDepths(communities, 0);
		});
	} 
	
	return {
		get: function() {
			return communities;
		},
		findCommunityById: function(id) {
			return findCommunityById(communities, id, []);
		},
		findCollectionById: function(id) {
			return findCollectionById(communities, id, []);
		},
		ready: function() {
			return ModelService.ready('community/all');
		},
		after: function(callback) {			
			ModelService.after('community/all', function() {
				callback();
			});
		}
	}
	
}]);
