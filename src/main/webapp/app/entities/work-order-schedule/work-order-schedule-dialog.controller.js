(function() {
    'use strict';

    angular
        .module('app')
        .controller('WorkOrderScheduleDialogController', WorkOrderScheduleDialogController);

    WorkOrderScheduleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkOrderSchedule', 'WorkOrder', 'WorkOrderHistory', 'Asset', 'WorkOrderTemplate'];

    function WorkOrderScheduleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WorkOrderSchedule, WorkOrder, WorkOrderHistory, Asset, WorkOrderTemplate) {
        var vm = this;

        vm.workOrderSchedule = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.workorders = WorkOrder.query();
        vm.workorderhistories = WorkOrderHistory.query();
        vm.assets = Asset.query();
        vm.workordertemplates = WorkOrderTemplate.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.workOrderSchedule.id !== null) {
                WorkOrderSchedule.update(vm.workOrderSchedule, onSaveSuccess, onSaveError);
            } else {
                WorkOrderSchedule.save(vm.workOrderSchedule, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:workOrderScheduleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;
        vm.datePickerOpenStatus.expireDate = false;
        vm.datePickerOpenStatus.completedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
