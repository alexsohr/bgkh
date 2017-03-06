(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetSpecificationTypeDialogController', AssetSpecificationTypeDialogController);

    AssetSpecificationTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AssetSpecificationType'];

    function AssetSpecificationTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AssetSpecificationType) {
        var vm = this;

        vm.assetSpecificationType = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.assetSpecificationType.id !== null) {
                AssetSpecificationType.update(vm.assetSpecificationType, onSaveSuccess, onSaveError);
            } else {
                AssetSpecificationType.save(vm.assetSpecificationType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:assetSpecificationTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
