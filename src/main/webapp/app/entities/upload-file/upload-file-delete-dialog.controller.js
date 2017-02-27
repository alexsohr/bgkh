(function() {
    'use strict';

    angular
        .module('app')
        .controller('UploadFileDeleteController',UploadFileDeleteController);

    UploadFileDeleteController.$inject = ['$uibModalInstance', 'entity', 'UploadFile'];

    function UploadFileDeleteController($uibModalInstance, entity, UploadFile) {
        var vm = this;

        vm.uploadFile = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UploadFile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
