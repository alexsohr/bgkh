"use strict";

angular.module('app').directive('nestedDataForm', function () {
    return {
        restrict: 'EA',
        replace: true,
        scope: {
            formId: '@formDataId',
            cssClass: '=formClass',
            title: '=formTitle',
            header: '=formHeader',
            body: '=formBody',
            footer: '=formFooter',
            callbackbuttonright: '&ngClickRightButton'
        },
        templateUrl: 'app/dashboard/workOrders/directives/nested-data-form.tpl.html',
        transclude: true,
        controller: function ($scope, $element) {
            $scope.reusableFormClass = "";
            $scope.reusableFormClass = $scope.cssClass;

            $scope.handler = $scope.formId;

            $scope.$watch('body', function () {
                var assetImportBody = $('.form-body', $element);
                assetImportBody.html($scope.body);
            });

            $scope.closeModal = function () {
                $scope.callbackbuttonright();
            }
        }
    };
});

