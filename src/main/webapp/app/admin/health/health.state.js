(function() {
    'use strict';

    angular
        .module('app.admin')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app.admin.jhi-health', {
            url: '/health',
            data: {
                authorities: ['ROLE_ADMIN'],
                title: 'health.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/admin/health/health.html',
                    controller: 'JhiHealthCheckController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
