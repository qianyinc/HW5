'use strict'

angular.module('app.controllers', [])

.controller('RandomQuoteCtrl', function($scope, QuoteService) {
	QuoteService.random()
		.$promise.then(function(quote) {
			$scope.quote = quote;
		});
})
.controller('SaveQuoteCtrl', function($scope, $state, QuoteService) {
    
    $scope.saveQuote = function() {
        QuoteService.save(
            $scope.quote,
            function(response) {
                $state.go("authorList", {});
            },
            function(err) {
                console.log(err);
            }
        );
    };
})
.controller('AuthorListCtrl', function($scope, AuthorService) {
	AuthorService.list()
    .$promise.then(function(authors) {
	    $scope.authors = authors;
    });

})
.controller('QuoteListCtrl', function($scope,  $stateParams, QuoteService) {
	QuoteService.by({name: $stateParams.name})
	    .$promise.then(function(quotes) {
		    $scope.quotes = quotes;
	    });

});