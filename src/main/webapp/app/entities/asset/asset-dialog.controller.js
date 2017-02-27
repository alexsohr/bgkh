(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetDialogController', AssetDialogController);

    AssetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asset', 'WorkOrder', 'User', 'UploadFile'];

    function AssetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Asset, WorkOrder, User, UploadFile) {
        var vm = this;

        vm.asset = entity;
        vm.clear = clear;
        vm.save = save;
        vm.workorders = WorkOrder.query();
        vm.users = User.query();
        vm.uploadfiles = UploadFile.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.asset.id !== null) {
                Asset.update(vm.asset, onSaveSuccess, onSaveError);
            } else {
                Asset.save(vm.asset, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:assetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();