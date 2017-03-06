(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('asset-specification-type-field', {
            parent: 'entity',
            url: '/asset-specification-type-field',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.assetSpecificationTypeField.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/asset-specification-type-field/asset-specification-type-fields.html',
                    controller: 'AssetSpecificationTypeFieldController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('assetSpecificationTypeField');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('asset-specification-type-field-detail', {
            parent: 'entity',
            url: '/asset-specification-type-field/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.assetSpecificationTypeField.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/asset-specification-type-field/asset-specification-type-field-detail.html',
                    controller: 'AssetSpecificationTypeFieldDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('assetSpecificationTypeField');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AssetSpecificationTypeField', function($stateParams, AssetSpecificationTypeField) {
                    return AssetSpecificationTypeField.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'asset-specification-type-field',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('asset-specification-type-field-detail.edit', {
            parent: 'asset-specification-type-field-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type-field/asset-specification-type-field-dialog.html',
                    controller: 'AssetSpecificationTypeFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AssetSpecificationTypeField', function(AssetSpecificationTypeField) {
                            return AssetSpecificationTypeField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asset-specification-type-field.new', {
            parent: 'asset-specification-type-field',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type-field/asset-specification-type-field-dialog.html',
                    controller: 'AssetSpecificationTypeFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fieldLable: null,
                                fieldName: null,
                                fieldType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('asset-specification-type-field', null, { reload: 'asset-specification-type-field' });
                }, function() {
                    $state.go('asset-specification-type-field');
                });
            }]
        })
        .state('asset-specification-type-field.edit', {
            parent: 'asset-specification-type-field',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type-field/asset-specification-type-field-dialog.html',
                    controller: 'AssetSpecificationTypeFieldDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AssetSpecificationTypeField', function(AssetSpecificationTypeField) {
                            return AssetSpecificationTypeField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('asset-specification-type-field', null, { reload: 'asset-specification-type-field' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('asset-specification-type-field.delete', {
            parent: 'asset-specification-type-field',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/asset-specification-type-field/asset-specification-type-field-delete-dialog.html',
                    controller: 'AssetSpecificationTypeFieldDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AssetSpecificationTypeField', function(AssetSpecificationTypeField) {
                            return AssetSpecificationTypeField.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('asset-specification-type-field', null, { reload: 'asset-specification-type-field' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
