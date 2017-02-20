(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeFieldDetailController', AssetSpecificationTypeFieldDetailController);

    AssetSpecificationTypeFieldDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AssetSpecificationTypeField', 'AssetSpecificationTypeValue', 'Asset'];

    function AssetSpecificationTypeFieldDetailController($scope, $rootScope, $stateParams, previousState, entity, AssetSpecificationTypeField, AssetSpecificationTypeValue, Asset) {
        var vm = this;

        vm.assetSpecificationTypeField = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('app:assetSpecificationTypeFieldUpdate', function(event, result) {
            vm.assetSpecificationTypeField = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
