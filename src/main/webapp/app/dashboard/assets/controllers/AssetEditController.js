(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetEditController', AssetEditController);

    AssetEditController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asset'];

    function AssetEditController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Asset) {
        var vm = this;

        vm.asset = entity;
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
            if (vm.asset.id !== null) {
                Asset.update(vm.asset, onSaveSuccess, onSaveError);
            } else {
                Asset.save(vm.asset, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('app:assetUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
