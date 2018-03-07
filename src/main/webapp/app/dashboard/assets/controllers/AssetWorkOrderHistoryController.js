(function () {
    'use strict';

    angular
        .module('app')
        .controller('AssetWorkOrderHistoryController', AssetWorkOrderHistoryController);

    AssetWorkOrderHistoryController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asset', 'AssetCopy', 'AssetImportModalService'];

    function AssetWorkOrderHistoryController($timeout, $scope, $stateParams, $uibModalInstance, entity, Asset, AssetCopy, AssetImportModalService) {
        var vm = this;

        vm.asset = entity;
        vm.assetList = {};
        vm.assetList.assetList = [];
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
            vm.assetList.assetList.push(vm.asset);
            vm.assetList.parentId = vm.asset.parentId;
            AssetCopy.save(vm.assetList, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
            $scope.$emit('app:assetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        function selectedParentTree(branch) {
            if (!angular.isUndefinedOrNull(branch)) {
                vm.asset.parentId = branch.id;
            }
        }
    }
})();
