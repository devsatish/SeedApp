seedapp.service('HttpService', [ '$http', function($http) {
	
	return {
		request: function(method, url, headers, data, dataType, success, failure) {			
			return $http({
				method: method,
				url: url,
				headers: headers,
				data: typeof data == "object" ? angular.toJson(data) : data,
				dataType: dataType
			}).success(function(data, status, headers, config) {
				success(data);
			}).error(function(data, status, headers, config) {
				if(failure) failure(typeof data.message != 'undefined' ? data.message : data);				
			});
		}
	}
	
}]);
