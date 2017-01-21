(function() {
    'use strict';

    angular
        .module('app.admin')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app.admin.docs', {
            url: '/docs',
            data: {
                authorities: ['ROLE_ADMIN'],
                title: 'global.menu.admin.apidocs'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/admin/docs/docs.html'
                }
            },
            resolve: {
            }
        });
    }
})();
