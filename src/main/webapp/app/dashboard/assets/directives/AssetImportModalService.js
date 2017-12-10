angular
    .module('app').service('AssetImportModalService', function ($uibModal, Asset, WorkOrderTemplateByAssetType, WorkOrderByAsset) {
    var open = false,
        modalInstance;

    this.isOpen = function () {
        return open;
    };

    this.close = function (result) {
        modalInstance.close(result);
    };

    this.dismiss = function (reason) {
        modalInstance.dismiss(reason);
    };

    this.openWorkOrderAssignment = function (id, assetSpecificationTypeId) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/assets/directives/work-order-assignment-form.tpl.html',
            controller: 'WorkOrderAssignmentController',
            controllerAs: 'workOrderAssignmentVm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                workOrders: ['WorkOrderByAsset', function (WorkOrderByAsset) {
                    return WorkOrderByAsset.query({id: id}).$promise;
                }],
                workOrderTemplates: ['WorkOrderTemplateByAssetType', function (WorkOrderTemplateByAssetType) {
                    return WorkOrderTemplateByAssetType.query({id: assetSpecificationTypeId}).$promise;
                }],
                assetId: id,
                assetTypeId: assetSpecificationTypeId
            }
        }).closed.then(function () {
            open = false;
        });

        //Set open
        open = true;

        //Set modalInstance
        modalInstance = modal;

        //Modal is closed/resolved/dismissed

        return modal;
    }

    this.openManageWorkOrder = function (id, assetSpecificationTypeId) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/assets/directives/work-order-switch-form.tpl.html',
            controller: 'WorkOrderSwitchController',
            controllerAs: 'workOrderSwitchVm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                workOrders: ['WorkOrderByAsset', function (WorkOrderByAsset) {
                    return WorkOrderByAsset.query({id: id}).$promise;
                }],
                workOrderTemplates: ['WorkOrderTemplateByAssetType', function (WorkOrderTemplateByAssetType) {
                    return WorkOrderTemplateByAssetType.query({id: assetSpecificationTypeId}).$promise;
                }],
                assetId: id,
                assetTypeId: assetSpecificationTypeId
            }
        }).closed.then(function () {
            open = false;
        });

        //Set open
        open = true;

        //Set modalInstance
        modalInstance = modal;

        //Modal is closed/resolved/dismissed

        return modal;
    }

    this.openMove = function (id) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/assets/directives/asset-move-form.tpl.html',
            controller: 'AssetMoveController',
            controllerAs: 'assetMoveVm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                entity: ['Asset', function (Asset) {
                    return Asset.get({id: id}).$promise;
                }]
            }
        }).closed.then(function () {
            open = false;
        });

        //Set open
        open = true;

        //Set modalInstance
        modalInstance = modal;

        //Modal is closed/resolved/dismissed

        return modal;
    }

    this.openCopy = function (id) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/assets/directives/asset-copy-form.tpl.html',
            controller: 'AssetCopyController',
            controllerAs: 'assetCopyVm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                entity: ['Asset', function (Asset) {
                    return Asset.get({id: id}).$promise;
                }]
            }
        }).closed.then(function () {
            open = false;
        });

        //Set open
        open = true;

        //Set modalInstance
        modalInstance = modal;

        //Modal is closed/resolved/dismissed

        return modal;
    }

    this.openEdit = function (id) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/assets/directives/asset-edit-form.tpl.html',
            controller: 'AssetEditController',
            controllerAs: 'assetEditVm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                entity: ['Asset', function (Asset) {
                    return Asset.get({id: id}).$promise;
                }]
            }
        }).closed.then(function () {
            open = false;
        });

        //Set open
        open = true;

        //Set modalInstance
        modalInstance = modal;

        //Modal is closed/resolved/dismissed

        return modal;
    }

    this.openDetails = function (id) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/assets/directives/asset-detail-form.tpl.html',
            controller: 'AssetEditController',
            controllerAs: 'assetEditVm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                entity: ['Asset', function (Asset) {
                    return Asset.get({id: id}).$promise;
                }]
            }
        }).closed.then(function () {
            open = false;
        });

        //Set open
        open = true;

        //Set modalInstance
        modalInstance = modal;

        //Modal is closed/resolved/dismissed

        return modal;
    }

    this.open = function (size, template, content, backdrop, controller) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/assets/directives/asset-import-wizard.tpl.html',
            controller: 'AssetImportWizardController',
            controllerAs: 'assetVm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                entity: function () {
                    return {
                        parentId: null,
                        name: null,
                        code: null,
                        assetType: null,
                        capacity: null,
                        manufacture: null,
                        strategic: "false",
                        supervisorId: undefined,
                        technicianId: undefined,
                        year: null,
                        id: null
                    };
                }
            }
        }).closed.then(function () {
            open = false;
        });

        //Set open
        open = true;

        //Set modalInstance
        modalInstance = modal;

        //Modal is closed/resolved/dismissed

        return modal;
    }
});
