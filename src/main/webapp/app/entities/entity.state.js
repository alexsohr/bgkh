'use strict';

angular
    .module('app.entity', ['ui.router'])
    .config(function ($stateProvider) {
        $stateProvider
            .state('app.entity', {
                abstract: true
            });
    });
