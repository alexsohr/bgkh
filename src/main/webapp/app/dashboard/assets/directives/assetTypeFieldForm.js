"use strict";

angular.module('app').directive('assetTypeFieldForm', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'app/dashboard/assets/directives/asset-type-field-form.tpl.html',
        scope: {
            asset: '=?asset',
            assetTypeFields: '=?assetTypeFields',
            units: '=?units',
            assetTypeValues: '=?assetTypeValues',
            disableForm: '=?disableForm',
            destroyElement: '=?destroyElement'

        },
        controller: function ($scope) {

        },
        link: function (scope, rootScope, element, attrs) {
            scope.assetSpecTypeFieldVals = [];
            scope.capacityUnits = [];
            if (!angular.isUndefinedOrNull(scope.units)) {
                scope.capacityUnits = scope.units;
            }

            if (!angular.isUndefinedOrNull(scope.assetTypeFields)) {
                for (var i = 0; i < scope.assetTypeFields.length; i++) {
                    if (!angular.isUndefinedOrNull(scope.assetTypeFields[i].fieldLabel)) {
                        var formData = {
                            typeId: scope.assetTypeFields[i].id,
                            fieldLabel: scope.assetTypeFields[i].fieldLabel,
                            capacityUnit: scope.assetTypeFields[i].capacityUnit
                        };
                        scope.assetTypeValues.forEach(function(assetTypeValue) {
                            if (assetTypeValue.assetSpecificationTypeFieldId === scope.assetTypeFields[i].id) {
                                formData.valId = !angular.isUndefinedOrNull(assetTypeValue.id) ? assetTypeValue.id : "";
                                formData.value = !angular.isUndefinedOrNull(assetTypeValue.fieldValue) ? assetTypeValue.fieldValue : "";
                                console.log(assetTypeValue.id)
                            }
                        });
                        scope.assetSpecTypeFieldVals.push(formData);
                    }
                }
                console.log(scope.assetSpecTypeFieldVals)
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
                        var dataObject = {
                            fieldId: data.typeId,
                            fieldLabel: data.fieldLabel,
                            capacityUnit: data.capacityUnit,
                            fieldType: 'string'
                        };
                        if (!angular.isUndefinedOrNull(scope.asset.id)) {
                            dataObject.valueId = data.valId;
                            dataObject.assetId = scope.asset.id;
                        }
                        dataObject.assetSpecificationTypeFieldId = scope.asset.assetSpecificationTypeId;
                        dataObject.fieldValue = data.value;
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
})
;
