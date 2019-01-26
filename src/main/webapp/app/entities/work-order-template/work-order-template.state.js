(function() {
    'use strict';

    angular
        .module('app')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('work-order-template', {
            parent: 'entity',
            url: '/work-order-template?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.workOrderTemplate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-order-template/work-order-templates.html',
                    controller: 'WorkOrderTemplateController',
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
                    $translatePartialLoader.addPart('workOrderTemplate');
                    $translatePartialLoader.addPart('workOrderTemplateType');
                    $translatePartialLoader.addPart('workOrderTemplateFunctionType');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('work-order-template-detail', {
            parent: 'entity',
            url: '/work-order-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.workOrderTemplate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/work-order-template/work-order-template-detail.html',
                    controller: 'WorkOrderTemplateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('workOrderTemplate');
                    $translatePartialLoader.addPart('workOrderTemplateType');
                    $translatePartialLoader.addPart('workOrderTemplateFunctionType');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WorkOrderTemplate', function($stateParams, WorkOrderTemplate) {
                    return WorkOrderTemplate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'work-order-template',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('work-order-template-detail.edit', {
            parent: 'work-order-template-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-template/work-order-template-dialog.html',
                    controller: 'WorkOrderTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkOrderTemplate', function(WorkOrderTemplate) {
                            return WorkOrderTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-order-template.new', {
            parent: 'work-order-template',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-template/work-order-template-dialog.html',
                    controller: 'WorkOrderTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numberOfDays: null,
                                hoursOfUsage: null,
                                description: null,
                                dueDays: null,
                                workOrderType: null,
                                functionType: null,
                                name: null,
                                shortDesc: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('work-order-template', null, { reload: 'work-order-template' });
                }, function() {
                    $state.go('work-order-template');
                });
            }]
        })
        .state('work-order-template.edit', {
            parent: 'work-order-template',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-template/work-order-template-dialog.html',
                    controller: 'WorkOrderTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WorkOrderTemplate', function(WorkOrderTemplate) {
                            return WorkOrderTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-order-template', null, { reload: 'work-order-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('work-order-template.delete', {
            parent: 'work-order-template',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/work-order-template/work-order-template-delete-dialog.html',
                    controller: 'WorkOrderTemplateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WorkOrderTemplate', function(WorkOrderTemplate) {
                            return WorkOrderTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('work-order-template', null, { reload: 'work-order-template' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
