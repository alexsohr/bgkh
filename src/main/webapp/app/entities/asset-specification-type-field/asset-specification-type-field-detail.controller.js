(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeFieldDetailController', AssetSpecificationTypeFieldDetailController);

    AssetSpecificationTypeFieldDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AssetSpecificationTypeField', 'AssetSpecificationType'];

    function AssetSpecificationTypeFieldDetailController($scope, $rootScope, $stateParams, previousState, entity, AssetSpecificationTypeField, AssetSpecificationType) {
        var vm = this;

        vm.assetSpecificationTypeField = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:assetSpecificationTypeFieldUpdate', function(event, result) {
            vm.assetSpecificationTypeField = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
