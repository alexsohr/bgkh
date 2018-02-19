(function() {
    'use strict';

    angular
        .module(workOrderSwitchVm)
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('work-order-schedule', {
            parent: 'entity',
            url: '/work-order-schedule',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.workOrderSchedule.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-order-schedule/work-order-schedules.html',
                    controller: 'WorkOrderScheduleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('workOrderSchedule');
                    $translatePartialLoader.addPart('scheduleStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('work-order-schedule-detail', {
            parent: 'entity',
            url: '/work-order-schedule/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.workOrderSchedule.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-order-schedule/work-order-schedule-detail.html',
                    controller: 'WorkOrderScheduleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('workOrderSchedule');
                    $translatePartialLoader.addPart('scheduleStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WorkOrderSchedule', function($stateParams, WorkOrderSchedule) {
                    return WorkOrderSchedule.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'work-order-schedule',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('work-order-schedule-detail.edit', {
            parent: 'work-order-schedule-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-schedule/work-order-schedule-dialog.html',
                    controller: 'WorkOrderScheduleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkOrderSchedule', function(WorkOrderSchedule) {
                            return WorkOrderSchedule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-order-schedule.new', {
            parent: 'work-order-schedule',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-schedule/work-order-schedule-dialog.html',
                    controller: 'WorkOrderScheduleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createDate: null,
                                expireDate: null,
                                description: null,
                                scheduleStatus: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('work-order-schedule', null, { reload: 'work-order-schedule' });
                }, function() {
                    $state.go('work-order-schedule');
                });
            }]
        })
        .state('work-order-schedule.edit', {
            parent: 'work-order-schedule',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-schedule/work-order-schedule-dialog.html',
                    controller: 'WorkOrderScheduleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkOrderSchedule', function(WorkOrderSchedule) {
                            return WorkOrderSchedule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-order-schedule', null, { reload: 'work-order-schedule' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-order-schedule.delete', {
            parent: 'work-order-schedule',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-schedule/work-order-schedule-delete-dialog.html',
                    controller: 'WorkOrderScheduleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WorkOrderSchedule', function(WorkOrderSchedule) {
                            return WorkOrderSchedule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-order-schedule', null, { reload: 'work-order-schedule' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
