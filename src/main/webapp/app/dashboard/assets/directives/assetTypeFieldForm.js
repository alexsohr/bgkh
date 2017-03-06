"use strict";

angular.module('app').directive('assetTypeFieldForm', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'app/dashboard/assets/directives/asset-type-field-form.tpl.html',
        scope: {
            asset: '=?asset',
            assetTypeFields: '=?assetTypeFields',
            assetTypeValues: '=?assetTypeValues',
            destroyElement: '=?destroyElement'

        },
        controller: function ($scope) {

        },
        link: function (scope, rootScope, element, attrs) {
            scope.asset.assetSpecificationTypeFields = [];

            if (!angular.isUndefinedOrNull(scope.assetTypeFields)) {
                for(var i = 0; i < scope.assetTypeFields.length; i++) {
                    scope.asset.assetSpecificationTypeFields.push({
                        typeId: scope.assetTypeFields[i].id,
                        name: scope.assetTypeFields[i].fieldLable,
                        valId: !angular.isUndefinedOrNull(scope.assetTypeValues) && scope.assetTypeValues.length > 0 ? scope.assetTypeValues[i].id : "",
                        value: !angular.isUndefinedOrNull(scope.assetTypeValues) && scope.assetTypeValues.length > 0 ? scope.assetTypeValues[i].fieldValue : ""
                    });
                }
            }

            scope.addNewChoice = function () {
                scope.asset.assetSpecificationTypeFields.push({});
            };

            scope.removeChoice = function (assetSpecificationTypeField) {
                var lastItem = scope.assetSpecificationTypeFields.indexOf(assetSpecificationTypeField);
                scope.asset.assetSpecificationTypeFields.splice(lastItem, 1);
            };

            scope.$watch(scope.destroyElement, function () {
                console.log("scope.destroyElement changed: " + scope.destroyElement);
                if (scope.destroyElement) {
                    scope.$destroy();
                }
            });
        }
    }
});
