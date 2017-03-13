(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeValueController', AssetSpecificationTypeValueController);

    AssetSpecificationTypeValueController.$inject = ['$scope', '$state', 'AssetSpecificationTypeValue'];

    function AssetSpecificationTypeValueController ($scope, $state, AssetSpecificationTypeValue) {
        var vm = this;

        vm.assetSpecificationTypeData = [];

        loadAll();

        function loadAll() {
            AssetSpecificationTypeValue.query(function(result) {
                vm.assetSpecificationTypeData = result;
                vm.searchQuery = null;
            });
        }
    }
})();
