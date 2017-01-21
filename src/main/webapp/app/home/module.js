"use strict";


angular
    .module('app.home', ['ui.router'])
    .config(function ($stateProvider) {

        $stateProvider
            .state('app.home', {
                url: '/home',
                data: {
                    authorities: [],
                    title: 'Home'
                },
                views: {
                    'content@app': {
                        templateUrl: 'app/home/welcome.html',
                        controller: 'HomeController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            });
    });


