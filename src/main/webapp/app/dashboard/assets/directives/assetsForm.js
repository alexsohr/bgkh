"use strict";

angular.module('app').directive('assetsForm', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'app/dashboard/assets/directives/asset-form.tpl.html',
        scope: {
            asset: '=asset',
            displayDetails: '=displayDetails',
            stepChange: '&',
            currentStep: '=currentStep',
            parentStep: '=parentStep'
        },
        controller: function ($scope) {

            $scope.state = "A";
            $scope.disableForm = false;

            if (!angular.isUndefinedOrNull($scope.displayDetails)) {
                $scope.disableForm = true;
            }

            $scope.hasSubTree = false;
            $scope.assetType = '0';
            if ($scope.asset) {
                $scope.assetType = $scope.asset.formType;
            }

            $scope.toggleAssetForm = function () {
                console.log($scope.assetType);
                if ('0' == $scope.assetType) {
                    $scope.hasSubTree = true;
                }
                else {
                    $scope.hasSubTree = false;
                }
            };

            $scope.$watch("asset", function (value) {
                $scope.toggleAssetForm();
            });
            $scope.$watch("hasNextStep", function (value) {
                if ($scope.stepChange) {
                    $scope.stepChange({"value": value});
                }
            });


            if (!angular.isUndefinedOrNull($scope.asset)) {
                $scope.state = "E";
                if (!angular.isUndefinedOrNull($scope.asset.children)) {
                    $scope.hasSubTree = $scope.asset.children.length > 0;
                }
            }
        },
        link: function (scope, rootScope, element, attrs) {

        }
    }
});
