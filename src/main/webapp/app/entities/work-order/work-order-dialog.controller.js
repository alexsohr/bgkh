(function() {
    'use strict';

    angular
        .module('app')
        .controller('WorkOrderDialogController', WorkOrderDialogController);

    WorkOrderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkOrder', 'Asset'];

    function WorkOrderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WorkOrder, Asset) {
        var vm = this;

        vm.workOrder = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.assets = Asset.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.workOrder.id !== null) {
                WorkOrder.update(vm.workOrder, onSaveSuccess, onSaveError);
            } else {
                WorkOrder.save(vm.workOrder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('app:workOrderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateCompleted = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
