(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('asset-specification-type-value', {
            parent: 'entity',
            url: '/asset-specification-type-value',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'app.assetSpecificationTypeValue.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/asset-specification-type-value/asset-specification-type-values.html',
                    controller: 'AssetSpecificationTypeValueController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('assetSpecificationTypeValue');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('asset-specification-type-value-detail', {
            parent: 'entity',
            url: '/asset-specification-type-value/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'app.assetSpecificationTypeValue.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/asset-specification-type-value/asset-specification-type-value-detail.html',
                    controller: 'AssetSpecificationTypeValueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('assetSpecificationTypeValue');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AssetSpecificationTypeValue', function($stateParams, AssetSpecificationTypeValue) {
                    return AssetSpecificationTypeValue.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'asset-specification-type-value',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('asset-specification-type-value-detail.edit', {
            parent: 'asset-specification-type-value-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type-value/asset-specification-type-value-dialog.html',
                    controller: 'AssetSpecificationTypeValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AssetSpecificationTypeValue', function(AssetSpecificationTypeValue) {
                            return AssetSpecificationTypeValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asset-specification-type-value.new', {
            parent: 'asset-specification-type-value',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type-value/asset-specification-type-value-dialog.html',
                    controller: 'AssetSpecificationTypeValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fieldValue: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('asset-specification-type-value', null, { reload: 'asset-specification-type-value' });
                }, function() {
                    $state.go('asset-specification-type-value');
                });
            }]
        })
        .state('asset-specification-type-value.edit', {
            parent: 'asset-specification-type-value',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type-value/asset-specification-type-value-dialog.html',
                    controller: 'AssetSpecificationTypeValueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AssetSpecificationTypeValue', function(AssetSpecificationTypeValue) {
                            return AssetSpecificationTypeValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('asset-specification-type-value', null, { reload: 'asset-specification-type-value' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asset-specification-type-value.delete', {
            parent: 'asset-specification-type-value',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type-value/asset-specification-type-value-delete-dialog.html',
                    controller: 'AssetSpecificationTypeValueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AssetSpecificationTypeValue', function(AssetSpecificationTypeValue) {
                            return AssetSpecificationTypeValue.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('asset-specification-type-value', null, { reload: 'asset-specification-type-value' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
