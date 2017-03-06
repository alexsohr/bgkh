(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeController', AssetSpecificationTypeController);

    AssetSpecificationTypeController.$inject = ['$scope', '$state', 'AssetSpecificationType'];

    function AssetSpecificationTypeController ($scope, $state, AssetSpecificationType) {
        var vm = this;

        vm.assetSpecificationTypes = [];

        loadAll();

        function loadAll() {
            AssetSpecificationType.query(function(result) {
                vm.assetSpecificationTypes = result;
                vm.searchQuery = null;
            });
        }
    }
})();
