(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeValueDetailController', AssetSpecificationTypeValueDetailController);

    AssetSpecificationTypeValueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AssetSpecificationTypeValue', 'AssetSpecificationTypeField', 'Asset'];

    function AssetSpecificationTypeValueDetailController($scope, $rootScope, $stateParams, previousState, entity, AssetSpecificationTypeValue, AssetSpecificationTypeField, Asset) {
        var vm = this;

        vm.assetSpecificationTypeValue = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:assetSpecificationTypeValueUpdate', function(event, result) {
            vm.assetSpecificationTypeValue = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
