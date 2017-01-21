"use strict";

angular.module('SmartAdmin.Modal', []).directive('reusableModal', function () {
    return {
        restrict: 'EA',
        replace: true,
        scope: {
            formId: '@modalDataId',
            title: '=modalTitle',
            header: '=modalHeader',
            cssClass: '=modalClass',
            body: '=modalBody',
            footer: '=modalFooter',
            callbackbuttonright: '&ngClickRightButton'
        },
        templateUrl: 'app/_common/modal/directives/reusable-modal.tpl.html',
        transclude: true,
        controller: function ($scope, $element) {
            $scope.reusableModalClass = "";
            $scope.reusableModalClass = $scope.cssClass;

            $scope.handler = $scope.formId;

            $scope.$watch('body', function () {
                var assetImportBody = $('.modal-body', $element);
                assetImportBody.html($scope.body);
            });

            $scope.closeModal = function () {
                $scope.callbackbuttonright();
            }
        }
    };
});

