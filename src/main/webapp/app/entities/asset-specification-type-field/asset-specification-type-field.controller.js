(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeFieldController', AssetSpecificationTypeFieldController);

    AssetSpecificationTypeFieldController.$inject = ['$scope', '$state', 'AssetSpecificationTypeField'];

    function AssetSpecificationTypeFieldController ($scope, $state, AssetSpecificationTypeField) {
        var vm = this;

        vm.assetSpecificationTypeFields = [];

        loadAll();

        function loadAll() {
            AssetSpecificationTypeField.query(function(result) {
                vm.assetSpecificationTypeFields = result;
                vm.searchQuery = null;
            });
        }
    }
})();
