(function() {
    'use strict';

    angular
        .module(workOrderSwitchVm)
        .controller('WorkOrderScheduleController', WorkOrderScheduleController);

    WorkOrderScheduleController.$inject = ['$scope', '$state', 'WorkOrderSchedule'];

    function WorkOrderScheduleController ($scope, $state, WorkOrderSchedule) {
        var vm = this;

        vm.workOrderSchedules = [];

        loadAll();

        function loadAll() {
            WorkOrderSchedule.query(function(result) {
                vm.workOrderSchedules = result;
                vm.searchQuery = null;
            });
        }
    }
})();
