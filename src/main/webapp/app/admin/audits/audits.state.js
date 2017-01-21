(function() {
    'use strict';

    angular
        .module('app.admin')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app.admin.audits', {
            url: '/audits',
            data: {
                authorities: ['ROLE_ADMIN'],
                title: 'audits.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/admin/audits/audits.html',
                    controller: 'AuditsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
