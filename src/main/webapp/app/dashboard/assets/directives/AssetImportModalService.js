angular
    .module('app').service('AssetImportModalService', function($uibModal) {
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

    this.open = function(size, template, content, backdrop, controller) {
        var modal = $uibModal.open({
            templateUrl: 'app/dashboard/assets/directives/asset-import-wizard.tpl.html',
            controller: 'AssetImportWizardController',
            controllerAs: 'assetVm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
                entity: function () {
                    return {
                    };
                }
            }
        }).closed.then(function() {
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
