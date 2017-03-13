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
            disableForm: '=?disableForm',
            destroyElement: '=?destroyElement'

        },
        controller: function ($scope) {

        },
        link: function (scope, rootScope, element, attrs) {
            scope.assetSpecTypeFieldVals = [];

            if (!angular.isUndefinedOrNull(scope.assetTypeFields)) {
                for (var i = 0; i < scope.assetTypeFields.length; i++) {
                    scope.assetSpecTypeFieldVals.push({
                        typeId: scope.assetTypeFields[i].id,
                        fieldLabel: scope.assetTypeFields[i].fieldLabel,
                        valId: !angular.isUndefinedOrNull(scope.assetTypeValues) && scope.assetTypeValues.length > 0 ? scope.assetTypeValues[i].id : "",
                        value: !angular.isUndefinedOrNull(scope.assetTypeValues) && scope.assetTypeValues.length > 0 ? scope.assetTypeValues[i].fieldValue : ""
                    });
                }
            }

            scope.addNewChoice = function () {
                scope.assetSpecTypeFieldVals.push({});
            };

            scope.removeChoice = function (assetSpecificationTypeField) {
                var lastItem = scope.assetSpecTypeFieldVals.indexOf(assetSpecificationTypeField);
                scope.assetSpecTypeFieldVals.splice(lastItem, 1);
            };

            scope.$watchCollection('assetSpecTypeFieldVals', function (newData, oldData) {
                scope.changeFormValue();
            });
            scope.changeFormValue = function () {
                scope.asset.assetSpecificationTypeData = [];
                for (var i = 0; i < scope.assetSpecTypeFieldVals.length; i++) {
                    var data = scope.assetSpecTypeFieldVals[i];
                    var dataObject = {fieldId: data.typeId, fieldLabel: data.fieldLabel, fieldType: 'string'};
                    if (!angular.isUndefinedOrNull(scope.asset.id)) {
                        dataObject.valueId = data.valId;
                        dataObject.fieldValue = data.value;
                        dataObject.assetSpecificationTypeFieldId = scope.asset.assetSpecificationTypeId;
                        dataObject.assetId = scope.asset.id;
                    }
                    scope.asset.assetSpecificationTypeData.push(dataObject);
                }
            }
            scope.$watch(scope.destroyElement, function () {
                console.log("scope.destroyElement changed: " + scope.destroyElement);
                if (scope.destroyElement) {
                    scope.$destroy();
                }
            });
        }
    }
});
