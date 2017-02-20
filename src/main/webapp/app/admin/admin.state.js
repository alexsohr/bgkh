"use strict";

angular
    .module('app.admin', ['ui.router'])
    .config(function ($stateProvider) {
        $stateProvider
            .state('app.admin', {
                abstract: true
            });
    });

