'use strict';

angular
    .module('app')
    .controller('AssetImportWizardController', function ($scope, $compile, $rootScope, $timeout, $stateParams, $uibModalInstance, entity ,Asset, AssetImport) {
        var assetVm = this;

        assetVm.assetList = [];
        assetVm.asset = entity;
        assetVm.clear = clear;
        assetVm.save = save;
        assetVm.importAssets = importAssets;



        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function importAssets() {
            assetVm.isSaving = true;
            console.log(assetVm.assetList);
            AssetImport.save(assetVm.assetList, onSaveSuccess, onSaveError);
        }

        function save () {
            assetVm.isSaving = true;
            if (assetVm.asset.id !== null) {
                Asset.update(assetVm.asset, onSaveSuccess, onSaveError);
            } else {
                Asset.save(assetVm.asset, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            console.log("Form data saved");
            $scope.$emit('app:assetUpdate', result);
            $uibModalInstance.close(result);
            assetVm.isSaving = false;
        }

        function onSaveError () {
            assetVm.isSaving = false;
        }
    });
