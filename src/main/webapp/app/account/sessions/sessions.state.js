(function() {
    'use strict';

    angular
        .module('app.account')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app.account.sessions', {
            url: '/sessions',
            data: {
                authorities: ['ROLE_USER'],
                title: 'global.menu.account.sessions'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/account/sessions/sessions.html',
                    controller: 'SessionsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
