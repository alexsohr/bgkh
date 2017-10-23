angular
    .module('app').service('WorkOrderAddModalService', function ($uibModal, WorkOrders) {
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


    this.openAdd = function (id) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/workOrders/directives/work-order-form.tpl.html',
            controller: 'WorkOrderFormController',
            controllerAs: 'workOrderAddVm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                entity: function () {
                    return {};
                }
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
