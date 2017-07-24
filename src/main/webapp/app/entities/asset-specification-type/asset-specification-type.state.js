(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('asset-specification-type', {
            parent: 'entity',
            url: '/asset-specification-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.assetSpecificationType.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/asset-specification-type/asset-specification-types.html',
                    controller: 'AssetSpecificationTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('assetSpecificationType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('asset-specification-type-detail', {
            parent: 'entity',
            url: '/asset-specification-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.assetSpecificationType.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/asset-specification-type/asset-specification-type-detail.html',
                    controller: 'AssetSpecificationTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('assetSpecificationType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AssetSpecificationType', function($stateParams, AssetSpecificationType) {
                    return AssetSpecificationType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'asset-specification-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('asset-specification-type-detail.edit', {
            parent: 'asset-specification-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type/asset-specification-type-dialog.html',
                    controller: 'AssetSpecificationTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AssetSpecificationType', function(AssetSpecificationType) {
                            return AssetSpecificationType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asset-specification-type.new', {
            parent: 'asset-specification-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type/asset-specification-type-dialog.html',
                    controller: 'AssetSpecificationTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                unit: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('asset-specification-type', null, { reload: 'asset-specification-type' });
                }, function() {
                    $state.go('asset-specification-type');
                });
            }]
        })
        .state('asset-specification-type.edit', {
            parent: 'asset-specification-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type/asset-specification-type-dialog.html',
                    controller: 'AssetSpecificationTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AssetSpecificationType', function(AssetSpecificationType) {
                            return AssetSpecificationType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('asset-specification-type', null, { reload: 'asset-specification-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asset-specification-type.delete', {
            parent: 'asset-specification-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type/asset-specification-type-delete-dialog.html',
                    controller: 'AssetSpecificationTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AssetSpecificationType', function(AssetSpecificationType) {
                            return AssetSpecificationType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('asset-specification-type', null, { reload: 'asset-specification-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
