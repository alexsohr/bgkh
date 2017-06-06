(function () {
    'use strict';

    angular
        .module('app')
        .controller('UserManagementDialogController', UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User'];

    function UserManagementDialogController($stateParams, $uibModalInstance, entity, User) {
        var vm = this;

        vm.authorities = ['ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_SUPERVISOR', 'ROLE_USER'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;


        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess(result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        function save() {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                if (vm.user.password !== vm.user.confirmPassword) {
                    vm.doNotMatch = 'ERROR';
                    vm.isSaving = false;
                } else {
                    if (vm.user.password === undefined || vm.user.password.trim() === "") {
                        vm.passwordError = 'ERROR';
                        vm.isSaving = false;
                    }
                    User.save(vm.user, onSaveSuccess, onSaveError);
                }
            }
        }
    }
})();
