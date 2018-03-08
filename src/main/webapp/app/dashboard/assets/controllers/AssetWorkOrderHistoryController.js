(function () {
    'use strict';

    angular
        .module('app')
        .controller('AssetWorkOrderHistoryController', AssetWorkOrderHistoryController);

    AssetWorkOrderHistoryController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asset', 'AssetCopy', 'AssetImportModalService'];

    function AssetWorkOrderHistoryController($timeout, $scope, $stateParams, $uibModalInstance, entity, Asset, AssetCopy, AssetImportModalService) {
        var vm = this;

        vm.entity = entity;
        vm.workOrderSchedules = [];
        function transformData() {
            angular.forEach(vm.entity, function (workOrderHistory) {
                if (vm.workOrderSchedules[workOrderHistory.workOrderSchedule.id] === undefined) {
                    vm.workOrderSchedules[workOrderHistory.workOrderSchedule.id] = [];
                    vm.workOrderSchedules[workOrderHistory.workOrderSchedule.id] = workOrderHistory.workOrderSchedule;
                    vm.workOrderSchedules[workOrderHistory.workOrderSchedule.id].workOrderHistories = [];
                }
                vm.workOrderSchedules[workOrderHistory.workOrderSchedule.id].workOrderHistories.push(workOrderHistory);
            });
            vm.workOrderSchedules = vm.workOrderSchedules.filter(function(x){
                return (x !== (undefined || null || ''));
            });
        }
        transformData();
        vm.workOrderSchedules.reverse();
        console.log(vm.workOrderSchedules);
        vm.clear = clear;

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();
