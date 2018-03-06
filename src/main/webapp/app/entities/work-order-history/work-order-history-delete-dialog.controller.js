(function() {
    'use strict';

    angular
        .module('app')
        .controller('WorkOrderHistoryDeleteController',WorkOrderHistoryDeleteController);

    WorkOrderHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkOrderHistory'];

    function WorkOrderHistoryDeleteController($uibModalInstance, entity, WorkOrderHistory) {
        var vm = this;

        vm.workOrderHistory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WorkOrderHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
