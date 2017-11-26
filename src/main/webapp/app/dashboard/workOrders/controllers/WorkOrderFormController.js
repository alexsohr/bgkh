'use strict';

angular
    .module('app')
    .controller('WorkOrderFormController', function ($scope, $uibModalInstance, entity, disabled, $timeout, WorkOrderAssetSpecificationType, WorkOrderTemplate) {
        var vm = this;

        vm.workOrderTemplate = entity;
        vm.clear = clear;
        vm.save = save;
        vm.disabled = false;
        console.dir(disabled);
        if (!angular.isUndefinedOrNull(disabled)) {
            vm.disabled = disabled;
        }
        vm.assetspecificationtypes = WorkOrderAssetSpecificationType.query();

        if (angular.isUndefinedOrNull(vm.workOrderTemplate.id)) {
            vm.workOrderTemplate.workOrderType = "PM";
            vm.workOrderTemplate.functionType = "DAILY";
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.workOrderTemplate.id !== null) {
                WorkOrderTemplate.update(vm.workOrderTemplate, onSaveSuccess, onSaveError);
            } else {
                WorkOrderTemplate.save(vm.workOrderTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:workOrderTemplateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    });
