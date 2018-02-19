(function() {
    'use strict';

    angular
        .module(workOrderSwitchVm)
        .controller('WorkOrderScheduleDetailController', WorkOrderScheduleDetailController);

    WorkOrderScheduleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkOrderSchedule', 'WorkOrder', 'WorkOrderHistory'];

    function WorkOrderScheduleDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkOrderSchedule, WorkOrder, WorkOrderHistory) {
        var vm = this;

        vm.workOrderSchedule = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:workOrderScheduleUpdate', function(event, result) {
            vm.workOrderSchedule = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
