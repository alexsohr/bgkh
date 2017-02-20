(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeValueDeleteController',AssetSpecificationTypeValueDeleteController);

    AssetSpecificationTypeValueDeleteController.$inject = ['$uibModalInstance', 'entity', 'AssetSpecificationTypeValue'];

    function AssetSpecificationTypeValueDeleteController($uibModalInstance, entity, AssetSpecificationTypeValue) {
        var vm = this;

        vm.assetSpecificationTypeValue = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AssetSpecificationTypeValue.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
