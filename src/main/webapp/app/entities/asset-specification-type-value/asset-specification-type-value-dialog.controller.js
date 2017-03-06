(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeValueDialogController', AssetSpecificationTypeValueDialogController);

    AssetSpecificationTypeValueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AssetSpecificationTypeValue', 'AssetSpecificationTypeField', 'Asset'];

    function AssetSpecificationTypeValueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AssetSpecificationTypeValue, AssetSpecificationTypeField, Asset) {
        var vm = this;

        vm.assetSpecificationTypeValue = entity;
        vm.clear = clear;
        vm.save = save;
        vm.assetspecificationtypefields = AssetSpecificationTypeField.query();
        vm.assets = Asset.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.assetSpecificationTypeValue.id !== null) {
                AssetSpecificationTypeValue.update(vm.assetSpecificationTypeValue, onSaveSuccess, onSaveError);
            } else {
                AssetSpecificationTypeValue.save(vm.assetSpecificationTypeValue, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:assetSpecificationTypeValueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
