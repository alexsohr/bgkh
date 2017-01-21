(function() {
    'use strict';

    angular
        .module('app.admin')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app.admin.logs', {
                url: '/logs',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    title: 'logs.title'
                },
            views: {
                'content@app': {
                    templateUrl: 'app/admin/logs/logs.html',
                    controller: 'LogsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
