"use strict";

angular.module('app').directive('workOrderAssetWidget', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'app/dashboard/workOrders/directives/work-order-asset-widget.tpl.html',
        scope: {
        },
        controller: function ($scope, $rootScope, $element, Assets) {

        },
        link: function (scope, rootScope, element, attrs) {

        }
    }
});
