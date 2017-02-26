(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('AssetController', AssetController);

    AssetController.$inject = ['$scope', '$state', 'Asset'];

    function AssetController ($scope, $state, Asset) {
        var vm = this;

        vm.assets = [];

        loadAll();

        function loadAll() {
            Asset.query(function(result) {
                vm.assets = result;
                vm.searchQuery = null;
            });
        }
    }
})();
