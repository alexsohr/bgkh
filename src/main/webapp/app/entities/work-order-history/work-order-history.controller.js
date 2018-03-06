(function() {
    'use strict';

    angular
        .module('app')
        .controller('WorkOrderHistoryController', WorkOrderHistoryController);

    WorkOrderHistoryController.$inject = ['$scope', '$state', 'WorkOrderHistory'];

    function WorkOrderHistoryController ($scope, $state, WorkOrderHistory) {
        var vm = this;

        vm.workOrderHistories = [];

        loadAll();

        function loadAll() {
            WorkOrderHistory.query(function(result) {
                vm.workOrderHistories = result;
                vm.searchQuery = null;
            });
        }
    }
})();
