(function() {
    'use strict';

    angular
        .module('app')
        .controller('WorkOrderDetailController', WorkOrderDetailController);

    WorkOrderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkOrder', 'Asset', 'WorkOrderTemplate', 'WorkOrderSchedule'];

    function WorkOrderDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkOrder, Asset, WorkOrderTemplate, WorkOrderSchedule) {
        var vm = this;

        vm.workOrder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:workOrderUpdate', function(event, result) {
            vm.workOrder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
