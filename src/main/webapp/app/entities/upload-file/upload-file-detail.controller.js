(function() {
    'use strict';

    angular
        .module('app')
        .controller('UploadFileDetailController', UploadFileDetailController);

    UploadFileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UploadFile', 'Asset'];

    function UploadFileDetailController($scope, $rootScope, $stateParams, previousState, entity, UploadFile, Asset) {
        var vm = this;

        vm.uploadFile = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('appApp:uploadFileUpdate', function(event, result) {
            vm.uploadFile = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
