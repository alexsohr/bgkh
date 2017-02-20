(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeValueController', AssetSpecificationTypeValueController);

    AssetSpecificationTypeValueController.$inject = ['$scope', '$state', 'AssetSpecificationTypeValue'];

    function AssetSpecificationTypeValueController ($scope, $state, AssetSpecificationTypeValue) {
        var vm = this;

        vm.assetSpecificationTypeValues = [];

        loadAll();

        function loadAll() {
            AssetSpecificationTypeValue.query(function(result) {
                vm.assetSpecificationTypeValues = result;
                vm.searchQuery = null;
            });
        }
    }
})();
