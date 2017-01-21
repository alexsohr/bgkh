(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app.account.password', {
            url: '/password',
            data: {
                authorities: ['ROLE_USER'],
                title: 'global.menu.account.password'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/account/password/password.html',
                    controller: 'PasswordController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
