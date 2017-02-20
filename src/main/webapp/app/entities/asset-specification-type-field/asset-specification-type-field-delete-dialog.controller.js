(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeFieldDeleteController',AssetSpecificationTypeFieldDeleteController);

    AssetSpecificationTypeFieldDeleteController.$inject = ['$uibModalInstance', 'entity', 'AssetSpecificationTypeField'];

    function AssetSpecificationTypeFieldDeleteController($uibModalInstance, entity, AssetSpecificationTypeField) {
        var vm = this;

        vm.assetSpecificationTypeField = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AssetSpecificationTypeField.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
