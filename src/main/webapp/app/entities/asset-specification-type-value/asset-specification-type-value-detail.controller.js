(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeValueDetailController', AssetSpecificationTypeValueDetailController);

    AssetSpecificationTypeValueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AssetSpecificationTypeValue', 'AssetSpecificationTypeField'];

    function AssetSpecificationTypeValueDetailController($scope, $rootScope, $stateParams, previousState, entity, AssetSpecificationTypeValue, AssetSpecificationTypeField) {
        var vm = this;

        vm.assetSpecificationTypeValue = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('app:assetSpecificationTypeValueUpdate', function(event, result) {
            vm.assetSpecificationTypeValue = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
