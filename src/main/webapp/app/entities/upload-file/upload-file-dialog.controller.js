(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('UploadFileDialogController', UploadFileDialogController);

    UploadFileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UploadFile'];

    function UploadFileDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UploadFile) {
        var vm = this;

        vm.uploadFile = entity;
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
            if (vm.uploadFile.id !== null) {
                UploadFile.update(vm.uploadFile, onSaveSuccess, onSaveError);
            } else {
                UploadFile.save(vm.uploadFile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('appApp:uploadFileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
