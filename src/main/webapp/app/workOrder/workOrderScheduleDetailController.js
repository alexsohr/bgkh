(function () {
    'use strict';

    angular
        .module('app.workOrder')
        .controller('workOrderScheduleDetailController', workOrderScheduleDetailController);

    workOrderScheduleDetailController.$inject = ['$rootScope', '$scope', '$state', '$uibModalInstance', 'entity', 'WorkOrderHistory'];

    function workOrderScheduleDetailController($rootScope, $scope, $state, $uibModalInstance, entity, WorkOrderHistory) {
        var vm = this;
        $scope.entity = entity;
        $scope.asset = entity.workOrder.asset;
        $scope.disableForm = true;
        $scope.newStatus = "";
        $scope.comment = "";
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.save = function () {
            vm.isSaving = true;
            var data = {};
            data.workOrderSchedule = entity;
            data.comment = $scope.comment;
            data.historyStatus = $scope.newStatus;
            WorkOrderHistory.save(data, onSaveSuccess, onSaveError);
        }

        var onSaveSuccess = function(result) {
            $scope.$emit('app:workOrderScheduleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        var onSaveError = function() {
            vm.isSaving = false;
        }

        vm.convertToExpDate = function(date) {
            return moment(date).fromNow();
        }
        vm.convertToDate = function(date) {
            return moment(date).format('ll');
        }
    }
})();
