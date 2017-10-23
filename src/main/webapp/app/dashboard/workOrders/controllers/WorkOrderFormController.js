'use strict';

angular
    .module('app')
    .controller('WorkOrderFormController', function ($scope, $uibModalInstance) {
        var vm = this;
        vm.clear = clear;
        $scope.workOrder = {};
        $scope.workOrder.workOrderType = "D";
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
    });
