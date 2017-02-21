(function() {
    'use strict';

    angular
        .module('app')
        .controller('UploadFileDialogController', UploadFileDialogController);

    UploadFileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UploadFile', 'Asset'];

    function UploadFileDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UploadFile, Asset) {
        var vm = this;

        vm.uploadFile = entity;
        vm.clear = clear;
        vm.save = save;
        vm.assets = Asset.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.uploadFile.id !== null) {
                UploadFile.update(vm.uploadFile, onSaveSuccess, onSaveError);
            } else {
                UploadFile.save(vm.uploadFile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('app:uploadFileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
