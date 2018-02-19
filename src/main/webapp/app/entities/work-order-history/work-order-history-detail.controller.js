(function() {
    'use strict';

    angular
        .module(workOrderSwitchVm)
        .controller('WorkOrderHistoryDetailController', WorkOrderHistoryDetailController);

    WorkOrderHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkOrderHistory', 'WorkOrderSchedule'];

    function WorkOrderHistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkOrderHistory, WorkOrderSchedule) {
        var vm = this;

        vm.workOrderHistory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:workOrderHistoryUpdate', function(event, result) {
            vm.workOrderHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
