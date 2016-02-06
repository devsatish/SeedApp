ascension.service('AuthService', [ function() {
	
	return {
		setRole: function(role) {
			sessionStorage.role = role;
		},
		getRole: function() {
			return sessionStorage.role ? sessionStorage.role : 'ROLE_ANONYMOUS';
		}
	}
	
}]);
