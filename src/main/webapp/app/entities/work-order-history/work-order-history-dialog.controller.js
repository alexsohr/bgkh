(function() {
    'use strict';

    angular
        .module(workOrderSwitchVm)
        .controller('WorkOrderHistoryDialogController', WorkOrderHistoryDialogController);

    WorkOrderHistoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkOrderHistory', 'WorkOrderSchedule'];

    function WorkOrderHistoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WorkOrderHistory, WorkOrderSchedule) {
        var vm = this;

        vm.workOrderHistory = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.workorderschedules = WorkOrderSchedule.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.workOrderHistory.id !== null) {
                WorkOrderHistory.update(vm.workOrderHistory, onSaveSuccess, onSaveError);
            } else {
                WorkOrderHistory.save(vm.workOrderHistory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:workOrderHistoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
