(function() {
    'use strict';

    angular
        .module('app')
        .controller('WorkOrderTemplateDetailController', WorkOrderTemplateDetailController);

    WorkOrderTemplateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'WorkOrderTemplate', 'AssetSpecificationType'];

    function WorkOrderTemplateDetailController($scope, $rootScope, $stateParams, previousState, entity, WorkOrderTemplate, AssetSpecificationType) {
        var vm = this;

        vm.workOrderTemplate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:workOrderTemplateUpdate', function(event, result) {
            vm.workOrderTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
