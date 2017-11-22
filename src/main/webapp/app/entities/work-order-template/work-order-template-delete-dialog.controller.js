(function() {
    'use strict';

    angular
        .module('app')
        .controller('WorkOrderTemplateDeleteController',WorkOrderTemplateDeleteController);

    WorkOrderTemplateDeleteController.$inject = ['$uibModalInstance', 'entity', 'WorkOrderTemplate'];

    function WorkOrderTemplateDeleteController($uibModalInstance, entity, WorkOrderTemplate) {
        var vm = this;

        vm.workOrderTemplate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WorkOrderTemplate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
