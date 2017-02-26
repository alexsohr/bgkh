(function() {
    'use strict';

    angular
        .module('appApp')
        .controller('UploadFileController', UploadFileController);

    UploadFileController.$inject = ['$scope', '$state', 'UploadFile'];

    function UploadFileController ($scope, $state, UploadFile) {
        var vm = this;

        vm.uploadFiles = [];

        loadAll();

        function loadAll() {
            UploadFile.query(function(result) {
                vm.uploadFiles = result;
                vm.searchQuery = null;
            });
        }
    }
})();
