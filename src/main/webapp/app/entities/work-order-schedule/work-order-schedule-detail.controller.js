(function() {
    'use strict';

    angular
        .module('app')
        .controller('WorkOrderScheduleDetailController', WorkOrderScheduleDetailController);

    WorkOrderScheduleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkOrderSchedule', 'WorkOrder', 'WorkOrderHistory', 'Asset', 'WorkOrderTemplate'];

    function WorkOrderScheduleDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkOrderSchedule, WorkOrder, WorkOrderHistory, Asset, WorkOrderTemplate) {
        var vm = this;

        vm.workOrderSchedule = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:workOrderScheduleUpdate', function(event, result) {
            vm.workOrderSchedule = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
