(function() {
    'use strict';

    angular
        .module('app.entity')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('app.entity.assetsTable', {
            url: '/asset',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'app.asset.home.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/asset/assets.html',
                    controller: 'AssetController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('asset-detail', {

            url: '/asset/{id}',
            data: {
                authorities: ['ROLE_USER'],
                title: 'app.asset.detail.title'
            },
            views: {
                'content@app': {
                    templateUrl: 'app/entities/asset/asset-detail.html',
                    controller: 'AssetDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Asset', function($stateParams, Asset) {
                    return Asset.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'asset',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('asset-detail.edit', {
            parent: 'asset-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset/asset-dialog.html',
                    controller: 'AssetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Asset', function(Asset) {
                            return Asset.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asset.new', {
            parent: 'asset',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset/asset-dialog.html',
                    controller: 'AssetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                parentId: null,
                                name: null,
                                code: null,
                                assetType: null,
                                capacity: null,
                                year: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('asset', null, { reload: 'asset' });
                }, function() {
                    $state.go('asset');
                });
            }]
        })
        .state('asset.edit', {
            parent: 'asset',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset/asset-dialog.html',
                    controller: 'AssetDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Asset', function(Asset) {
                            return Asset.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('asset', null, { reload: 'asset' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asset.delete', {
            parent: 'asset',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset/asset-delete-dialog.html',
                    controller: 'AssetDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Asset', function(Asset) {
                            return Asset.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('asset', null, { reload: 'asset' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
