"use strict";


angular
    .module('app.workOrder', ['ui.router', 'treeGrid'])
    .config(function ($stateProvider) {

        $stateProvider
            .state('app.workOrder', {
                url: '/workOrder',
                data: {
                    authorities: [],
                    title: 'Work Order'
                },
                views: {
                    'content@app': {
                        templateUrl: 'app/workOrder/list.tpl.html',
                        controller: 'WorkOrderTableController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            });
    });


