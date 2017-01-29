(function () {
    'use strict';

    angular
        .module('app')
        .controller('AssetCopyController', AssetCopyController);

    AssetCopyController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asset', 'AssetImportModalService'];

    function AssetCopyController($timeout, $scope, $stateParams, $uibModalInstance, entity, Asset, AssetImportModalService) {
        var vm = this;

        vm.asset = entity;
        vm.clear = clear;
        vm.save = save;
        vm.selectedParentTree = selectedParentTree;

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.asset.id !== null) {
                Asset.update(vm.asset, onSaveSuccess, onSaveError);
            } else {
                Asset.save(vm.asset, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('app:assetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
            AssetImportModalService.openEdit(result.id);
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        function selectedParentTree(branch) {
            if (!angular.isUndefinedOrNull(branch)) {
                vm.asset.id = null;
                vm.asset.parentId = branch.id;
            }
        }
    }
})();
