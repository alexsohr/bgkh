(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('upload-file', {
            parent: 'entity',
            url: '/upload-file',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'app.uploadFile.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/upload-file/upload-files.html',
                    controller: 'UploadFileController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uploadFile');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('upload-file-detail', {
            parent: 'entity',
            url: '/upload-file/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'app.uploadFile.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/upload-file/upload-file-detail.html',
                    controller: 'UploadFileDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uploadFile');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UploadFile', function($stateParams, UploadFile) {
                    return UploadFile.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'upload-file',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('upload-file-detail.edit', {
            parent: 'upload-file-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upload-file/upload-file-dialog.html',
                    controller: 'UploadFileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UploadFile', function(UploadFile) {
                            return UploadFile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('upload-file.new', {
            parent: 'upload-file',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upload-file/upload-file-dialog.html',
                    controller: 'UploadFileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                location: null,
                                deleted: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('upload-file', null, { reload: 'upload-file' });
                }, function() {
                    $state.go('upload-file');
                });
            }]
        })
        .state('upload-file.edit', {
            parent: 'upload-file',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upload-file/upload-file-dialog.html',
                    controller: 'UploadFileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UploadFile', function(UploadFile) {
                            return UploadFile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('upload-file', null, { reload: 'upload-file' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('upload-file.delete', {
            parent: 'upload-file',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/upload-file/upload-file-delete-dialog.html',
                    controller: 'UploadFileDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UploadFile', function(UploadFile) {
                            return UploadFile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('upload-file', null, { reload: 'upload-file' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
