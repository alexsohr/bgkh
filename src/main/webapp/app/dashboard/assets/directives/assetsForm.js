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
            currentStep: '=?currentStep',
            parentStep: '=parentStep',
            parentId: '=parentId',
            updateAssetCallback: '&?'
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

            if ($scope.updateAssetCallback !== undefined) {
                var index = {index: $scope.currentStep};
                $scope.asset = $scope.updateAssetCallback(index);
                console.log($scope.asset);
            }

            $scope.hasSubTree = false;
            $scope.assetType = 'ASSET_GROUP';
            if (!angular.isUndefinedOrNull($scope.asset) && $scope.asset.assetType == null) {
                $scope.asset.assetType = 'ASSET_GROUP';
            }

            $scope.years = [];
            $scope.selectedYear = 0;
            var j = 0;
            for (var i = 1970; i <= new Date().getFullYear(); i++) {
                $scope.years.push(i);
                if ($scope.asset.year == i) {
                    $scope.selectedYear = j;
                }
                j++;
            }

            console.log($scope.asset);
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


            if (!angular.isUndefinedOrNull($scope.asset.id)) {
                $scope.state = "E";
                if (!angular.isUndefinedOrNull($scope.asset.children)) {
                    $scope.hasSubTree = $scope.asset.children.length > 0;
                }
            }
        },
        link: function (scope, rootScope, element, attrs) {

        }
    }
})
.directive('convertToString', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (val) {
                return '' + val;
            });
            ngModel.$formatters.push(function (val) {
                return parseInt(val, 10);
            });
        }
    }
});
