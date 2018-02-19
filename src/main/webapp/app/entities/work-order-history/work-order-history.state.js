(function() {
    'use strict';

    angular
        .module(workOrderSwitchVm)
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('work-order-history', {
            parent: 'entity',
            url: '/work-order-history',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.workOrderHistory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-order-history/work-order-histories.html',
                    controller: 'WorkOrderHistoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('workOrderHistory');
                    $translatePartialLoader.addPart('historyStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('work-order-history-detail', {
            parent: 'entity',
            url: '/work-order-history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.workOrderHistory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-order-history/work-order-history-detail.html',
                    controller: 'WorkOrderHistoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('workOrderHistory');
                    $translatePartialLoader.addPart('historyStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WorkOrderHistory', function($stateParams, WorkOrderHistory) {
                    return WorkOrderHistory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'work-order-history',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('work-order-history-detail.edit', {
            parent: 'work-order-history-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-history/work-order-history-dialog.html',
                    controller: 'WorkOrderHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkOrderHistory', function(WorkOrderHistory) {
                            return WorkOrderHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-order-history.new', {
            parent: 'work-order-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-history/work-order-history-dialog.html',
                    controller: 'WorkOrderHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createDate: null,
                                historyStatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('work-order-history', null, { reload: 'work-order-history' });
                }, function() {
                    $state.go('work-order-history');
                });
            }]
        })
        .state('work-order-history.edit', {
            parent: 'work-order-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-history/work-order-history-dialog.html',
                    controller: 'WorkOrderHistoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkOrderHistory', function(WorkOrderHistory) {
                            return WorkOrderHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-order-history', null, { reload: 'work-order-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-order-history.delete', {
            parent: 'work-order-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-history/work-order-history-delete-dialog.html',
                    controller: 'WorkOrderHistoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WorkOrderHistory', function(WorkOrderHistory) {
                            return WorkOrderHistory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-order-history', null, { reload: 'work-order-history' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
