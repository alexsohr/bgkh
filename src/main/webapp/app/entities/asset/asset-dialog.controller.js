(function() {
    'use strict';

    angular
        .module('app')
        .controller('AssetDialogController', AssetDialogController);

    AssetDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Asset', 'WorkOrder'];

    function AssetDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Asset, WorkOrder) {
        var vm = this;

        vm.asset = entity;
        vm.clear = clear;
        vm.save = save;
        vm.assetspecificationtypefields = AssetSpecificationTypeField.query({filter: 'assetspecificationtypefield-is-null'});
        $q.all([vm.asset.$promise, vm.assetspecificationtypefields.$promise]).then(function() {
            if (!vm.asset.assetSpecificationTypeFieldId) {
                return $q.reject();
            }
            return AssetSpecificationTypeField.get({id : vm.asset.assetSpecificationTypeFieldId}).$promise;
        }).then(function(assetSpecificationTypeField) {
            vm.assetspecificationtypefields.push(assetSpecificationTypeField);
        });
        vm.workorders = WorkOrder.query();

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
