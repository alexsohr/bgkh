"use strict";


angular
    .module('app.account', ['ui.router'])
    .config(function ($stateProvider) {
        $stateProvider
            .state('app.account', {
                abstract: true
            });
    });


