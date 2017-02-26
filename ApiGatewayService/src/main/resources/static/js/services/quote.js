'use strict'

angular.module('app.services', ['ngResource'])

.factory('QuoteService', function($resource) {
	return $resource('/api/quote/:id', {id:'@_id'}, {
		random: {
			method: 'GET',
			url: '/api/quote/random'
		},
		by: {
			method: 'GET',
			url: '/api/quote/by/?name=:name',
			isArray: true
		}
	});
})
.factory('AuthorService', function($resource) {
	return $resource('/api/authors/:id', {id:'@_id'}, {
		list: {
			method: 'GET',
			url: '/api/author',
			isArray: true
		}
	});
});
