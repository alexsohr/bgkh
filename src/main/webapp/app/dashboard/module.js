'use strict';

angular.module('app.dashboard', ['ui.router', 'treeGrid'])
    .config(function ($stateProvider) {
        $stateProvider
            .state('app.dashboard', {
                url: '/dashboard',
                views: {
                    "content@app": {
                        controller: 'DashboardCtrl',
                        templateUrl: 'app/dashboard/dashboard.html',
                        resolve: {
                            notifications: function ($http, APP_CONFIG) {
                                return $http.get(APP_CONFIG.apiRootUrl + '/dashboard.json');
                            }
                        }
                    }
                },
                data: {
                    authorities: ['ROLE_ADMIN, ROLE_MANAGER, ROLE_SUPERVISOR'],
                    title: 'Dashboard'
                },
                resolve: {
                    scripts: function (lazyScript) {
                        return lazyScript.register([
                            'content/js/vendor.ui.js',
                            'content/js/vendor.datatables.js'
                        ]);
                    }
                }
            });
    });
