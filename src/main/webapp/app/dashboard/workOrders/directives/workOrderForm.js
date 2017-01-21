"use strict";

angular.module('app').directive('workOrderForm', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'app/dashboard/workOrders/directives/work-order-form.tpl.html',
        scope: {
            workOrder: '=workOrder',
            displayDetails: '=displayDetails'
        },
        controller: function ($scope, $rootScope, $element, Assets) {

            $scope.workOrderWeek = "EW";
            $scope.workOrderMonth = "EVM";
            $scope.workOrderDay = "EVD";
            $scope.workOrderState = "N";
            $scope.assets = [];
            $scope.selectedAsset = {};

            Assets.then(function (response) {
                var rawTreeData = response.data;
                var j = 0;
                for(var i = 0; i < rawTreeData.length; i++) {
                    if (rawTreeData[i].formType != "0") {
                        $scope.assets[j] = {id: rawTreeData[i].demographicId, name: rawTreeData[i].name};
                        if (!angular.isUndefinedOrNull($scope.workOrder) && rawTreeData[i].demographicId == $scope.workOrder.assetId) {
                            $scope.selectedAsset = $scope.assets[j];
                        }
                        j++;
                    }
                }
            });

            $scope.state = "A";
            $scope.disableForm = false;

            if (!angular.isUndefinedOrNull($scope.displayDetails)) {
                $scope.disableForm = true;
            }

            $scope.$watch("workOrder", function (value) {

            });


            if (!angular.isUndefinedOrNull($scope.workOrder)) {
                $scope.state = "E";

            }
        },
        link: function (scope, rootScope, element, attrs) {

        }
    }
});
