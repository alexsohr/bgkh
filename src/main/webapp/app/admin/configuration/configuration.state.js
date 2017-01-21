(function() {
    'use strict';

    angular
        .module('app.admin')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app.admin.jhi-configuration', {
            url: '/configuration',
            data: {
                authorities: ['ROLE_ADMIN'],
                title: 'configuration.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/admin/configuration/configuration.html',
                    controller: 'JhiConfigurationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
