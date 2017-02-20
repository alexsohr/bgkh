(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeFieldDialogController', AssetSpecificationTypeFieldDialogController);

    AssetSpecificationTypeFieldDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AssetSpecificationTypeField', 'AssetSpecificationTypeValue', 'Asset'];

    function AssetSpecificationTypeFieldDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AssetSpecificationTypeField, AssetSpecificationTypeValue, Asset) {
        var vm = this;

        vm.assetSpecificationTypeField = entity;
        vm.clear = clear;
        vm.save = save;
        vm.assetspecificationtypevalues = AssetSpecificationTypeValue.query();
        vm.assets = Asset.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.assetSpecificationTypeField.id !== null) {
                AssetSpecificationTypeField.update(vm.assetSpecificationTypeField, onSaveSuccess, onSaveError);
            } else {
                AssetSpecificationTypeField.save(vm.assetSpecificationTypeField, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('app:assetSpecificationTypeFieldUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
