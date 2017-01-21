(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app.account.settings', {
            url: '/settings',
            data: {
                authorities: ['ROLE_USER'],
                title: 'global.menu.account.settings'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/account/settings/settings.html',
                    controller: 'SettingsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
