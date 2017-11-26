'use strict';

angular
    .module('app')
    .controller('WorkOrderAssignmentController', function ($rootScope, $scope, $uibModalInstance, workOrders, workOrderTemplates, WorkOrderByAsset, assetId, assetTypeId, $timeout, WorkOrderAssetSpecificationType, WorkOrderTemplate) {
        var vm = this;

        vm.workOrderTemplates = workOrderTemplates;
        vm.workOrderAssigneds = workOrders;
        vm.assetId = assetId;
        vm.assetTypeId = assetTypeId;
        vm.clear = clear;
        vm.save = save;
        findAssignedWorkOrderTemplates();

        function findAssignedWorkOrderTemplates () {
            angular.forEach(vm.workOrderTemplates, function(workOrderTemplate) {
                if (angular.isObject(workOrderTemplate)) {
                    angular.forEach(vm.workOrderAssigneds, function(workOrderAssigned) {
                        if (angular.isObject(workOrderAssigned) && workOrderAssigned.workOrderTemplateId === workOrderTemplate.id) {
                            workOrderTemplate.assign = true;
                        }
                    });
                }
            });
        }
        console.dir(vm.workOrderTemplates);
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            var workOrders = [];
            angular.forEach(vm.workOrderTemplates, function(workOrderTemplate) {
                if (angular.isObject(workOrderTemplate) && workOrderTemplate.assign === true) {
                    var workOrder = {};
                    workOrder.assetId = vm.assetId;
                    workOrder.track = false;
                    workOrder.workOrderTemplateId = workOrderTemplate.id;
                    workOrders.push(workOrder);
                }
            });
            var workOrderDTO = {workOrders: workOrders, assetId: vm.assetId};
            WorkOrderByAsset.save(workOrderDTO, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('app:workOrderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
            $.smallBox({
                title: $rootScope.getWord("Data submitted successfully!"),
                content: "<i class='fa fa-clock-o'></i> <i>" + $rootScope.getWord('1 second ago') + "...</i>",
                color: "#5F895F",
                iconSmall: "fa fa-check bounce animated",
                timeout: 4000
            });
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    });
