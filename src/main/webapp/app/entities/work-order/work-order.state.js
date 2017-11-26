(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('work-order', {
            parent: 'entity',
            url: '/work-order?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.workOrder.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-order/work-orders.html',
                    controller: 'WorkOrderController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('workOrder');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('work-order-detail', {
            parent: 'entity',
            url: '/work-order/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.workOrder.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-order/work-order-detail.html',
                    controller: 'WorkOrderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('workOrder');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WorkOrder', function($stateParams, WorkOrder) {
                    return WorkOrder.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'work-order',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('work-order-detail.edit', {
            parent: 'work-order-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order/work-order-dialog.html',
                    controller: 'WorkOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkOrder', function(WorkOrder) {
                            return WorkOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-order.new', {
            parent: 'work-order',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order/work-order-dialog.html',
                    controller: 'WorkOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                track: false,
                                description: null,
                                trackDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('work-order', null, { reload: 'work-order' });
                }, function() {
                    $state.go('work-order');
                });
            }]
        })
        .state('work-order.edit', {
            parent: 'work-order',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order/work-order-dialog.html',
                    controller: 'WorkOrderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkOrder', function(WorkOrder) {
                            return WorkOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-order', null, { reload: 'work-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-order.delete', {
            parent: 'work-order',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order/work-order-delete-dialog.html',
                    controller: 'WorkOrderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WorkOrder', function(WorkOrder) {
                            return WorkOrder.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-order', null, { reload: 'work-order' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
