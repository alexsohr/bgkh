(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeDetailController', AssetSpecificationTypeDetailController);

    AssetSpecificationTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AssetSpecificationType'];

    function AssetSpecificationTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, AssetSpecificationType) {
        var vm = this;

        vm.assetSpecificationType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:assetSpecificationTypeUpdate', function(event, result) {
            vm.assetSpecificationType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
