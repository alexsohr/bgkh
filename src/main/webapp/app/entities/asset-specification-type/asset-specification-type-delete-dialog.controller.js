(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeDeleteController',AssetSpecificationTypeDeleteController);

    AssetSpecificationTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'AssetSpecificationType'];

    function AssetSpecificationTypeDeleteController($uibModalInstance, entity, AssetSpecificationType) {
        var vm = this;

        vm.assetSpecificationType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AssetSpecificationType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
