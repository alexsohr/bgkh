angular
    .module('app').service('WorkOrderScheduleDetailModalService', function ($uibModal, WorkOrderSchedule) {
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


    this.openDetails = function (id) {
        var modal = $uibModal.open({
            templateUrl: 'app/workOrder/work-order-schedule-detail-form.tpl.html',
            controller: 'workOrderScheduleDetailController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                entity: ['WorkOrderSchedule', function (WorkOrderSchedule) {
                    return WorkOrderSchedule.get({id: id}).$promise;
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
});
