(function() {
    'use strict';

    angular
        .module('app')
        .controller('WorkOrderScheduleDeleteController',WorkOrderScheduleDeleteController);

    WorkOrderScheduleDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkOrderSchedule'];

    function WorkOrderScheduleDeleteController($uibModalInstance, entity, WorkOrderSchedule) {
        var vm = this;

        vm.workOrderSchedule = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WorkOrderSchedule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
