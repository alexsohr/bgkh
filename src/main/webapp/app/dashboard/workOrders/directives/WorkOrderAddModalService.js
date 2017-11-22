angular
    .module('app').service('WorkOrderAddModalService', function ($uibModal) {
    var open = false,
        modalInstance;

    this.isOpen = function () {
        return open;
    };

    this.close = function (result) {
        modalInstance.close(result);
    };

    this.dismiss = function (reason) {
        modalInstance.dismiss(reason);
    };


    this.openEdit = function (id) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/workOrders/directives/work-order-form.tpl.html',
            controller: 'WorkOrderFormController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                entity: ['WorkOrderTemplate', function(WorkOrderTemplate) {
                    return WorkOrderTemplate.get({id : id}).$promise;
                }],
                disabled: false
            }
        }).closed.then(function () {
            open = false;
        });

        //Set open
        open = true;

        //Set modalInstance
        modalInstance = modal;

        //Modal is closed/resolved/dismissed

        return modal;
    }

    this.openDetails = function (id) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/workOrders/directives/work-order-form.tpl.html',
            controller: 'WorkOrderFormController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                entity: ['WorkOrderTemplate', function(WorkOrderTemplate) {
                    return WorkOrderTemplate.get({id : id}).$promise;
                }],
                disabled: true
            }
        }).closed.then(function () {
            open = false;
        });

        //Set open
        open = true;

        //Set modalInstance
        modalInstance = modal;

        //Modal is closed/resolved/dismissed

        return modal;
    }

    this.openAdd = function (id) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/workOrders/directives/work-order-form.tpl.html',
            controller: 'WorkOrderFormController',
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
                        id: null
                    };
                },
                disabled: false
            }
        }).closed.then(function () {
            open = false;
        });

        //Set open
        open = true;

        //Set modalInstance
        modalInstance = modal;

        //Modal is closed/resolved/dismissed

        return modal;
    }
});
