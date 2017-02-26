(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('AssetDetailController', AssetDetailController);

    AssetDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Asset', 'WorkOrder', 'User'];

    function AssetDetailController($scope, $rootScope, $stateParams, previousState, entity, Asset, WorkOrder, User) {
        var vm = this;

        vm.asset = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:assetUpdate', function(event, result) {
            vm.asset = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
