(function() {
    'use strict';

    angular
        .module('app.admin')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app.admin.jhi-metrics', {
            url: '/metrics',
            data: {
                authorities: ['ROLE_ADMIN'],
                title: 'metrics.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/admin/metrics/metrics.html',
                    controller: 'JhiMetricsMonitoringController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
