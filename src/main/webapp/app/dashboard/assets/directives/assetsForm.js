"use strict";

angular.module('app').directive('assetsForm', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'app/dashboard/assets/directives/asset-form.tpl.html',
        scope: {
            asset: '=?asset',
            displayDetails: '=displayDetails',
            stepChange: '&',
            currentStep: '=currentStep',
            parentStep: '=parentStep',
            parentId: '=parentId',
            updateAssetCallback: '&'
        },
        controller: function ($scope) {

            if (angular.isUndefinedOrNull($scope.currentStep)) {
                $scope.currentStep = 1;
            }
            $scope.state = "A";
            $scope.disableForm = false;

            if (!angular.isUndefinedOrNull($scope.displayDetails)) {
                $scope.disableForm = true;
            }

            if (angular.isFunction($scope.updateAssetCallback)) {
                var index = {index: $scope.currentStep};
                $scope.asset = $scope.updateAssetCallback(index);
                console.log($scope.asset);
            }

            $scope.hasSubTree = false;
            $scope.assetType = 'ASSET_GROUP';
            if (!angular.isUndefinedOrNull($scope.asset) && $scope.asset.assetType == null) {
                $scope.asset.assetType = 'ASSET_GROUP';
            }



            $scope.toggleAssetForm = function () {
                console.log($scope.asset.assetType);
                if ('ASSET_GROUP' == $scope.asset.assetType) {
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
