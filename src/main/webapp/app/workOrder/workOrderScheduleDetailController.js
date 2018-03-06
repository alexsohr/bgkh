(function () {
    'use strict';

    angular
        .module('app.workOrder')
        .controller('workOrderScheduleDetailController', workOrderScheduleDetailController);

    workOrderScheduleDetailController.$inject = ['$rootScope', '$scope', '$state', '$uibModalInstance', 'entity'];

    function workOrderScheduleDetailController($rootScope, $scope, $state, $uibModalInstance, entity) {
        var vm = this;
        $scope.entity = entity;
        $scope.asset = entity.workOrder.asset;
        $scope.disableForm = true;
        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.save = function () {
            vm.isSaving = true;
            AssetCopy.save($scope.entity, onSaveSuccess, onSaveError);
        }

        vm.onSaveSuccess = function(result) {
            $scope.$emit('app:workOrderScheduleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        vm.onSaveError = function() {
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
