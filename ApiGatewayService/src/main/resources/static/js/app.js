'use strict'

var app = angular.module('app', [
    'ui.router',
    'ngResource',
    'app.services',
    'app.controllers'

]);

app.config(function($urlRouterProvider, $stateProvider) {

    $urlRouterProvider.otherwise("/");

    $stateProvider
        .state("quote", {
            url: "/quote",
            templateUrl : "templates/quote.html",
            controller: "RandomQuoteCtrl"
        })
        .state("add", {
            url: "/add",
            templateUrl : "templates/add.html",
            controller: "SaveQuoteCtrl"
        })
        .state("authorList", {
            url: "/",
            templateUrl : "templates/author_list.html",
            controller: "AuthorListCtrl"
        })
        .state("quoteList", {
            url: "/quotes/:name",
            templateUrl : "templates/quote_list.html",
            controller: "QuoteListCtrl"
        });
});