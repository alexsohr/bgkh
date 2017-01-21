(function() {
    'use strict';

    angular
        .module('app')
        .controller('WorkOrderDeleteController',WorkOrderDeleteController);

    WorkOrderDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkOrder'];

    function WorkOrderDeleteController($uibModalInstance, entity, WorkOrder) {
        var vm = this;

        vm.workOrder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WorkOrder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
