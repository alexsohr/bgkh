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
        controller: function ($scope, $filter, $compile, User, AssetManufacture, Upload, AssetSpecificationType, AssetSpecificationTypeFieldByType, AssetSpecificationTypeValue, AlertService) {

            if (angular.isUndefinedOrNull($scope.currentStep)) {
                $scope.currentStep = 1;
            }
            $scope.state = "A";
            $scope.disableForm = false;
            $scope.users = [];
            $scope.manufactures = [];
            $scope.assetSpecificationTypes = [];
            $scope.assetSpecificationTypeFields = [];
            $scope.assetSpecificationTypeValues = [];
            $scope.destroySpecTypeElement = false;
            $scope.assetSpecificationTypeSelect = true;

            if ($scope.updateAssetCallback !== undefined) {
                var index = {index: $scope.currentStep};
                $scope.asset = $scope.updateAssetCallback(index);
                console.log($scope.asset);
            }

            loadAllAssetSpecificationTypes();
            loadAllManufactures();
            loadAllUsers();

            addTypeFieldElements();

            $scope.changeToNewSpecType = function () {
                $scope.destroySpecTypeElement = true;
                $scope.assetSpecificationTypeSelect = false;
                $scope.assetSpecificationTypeValues = [];
                $scope.assetSpecificationTypeFields = [];
                $scope.destroySpecTypeElement = false;
                addTypeFieldElements();
            };

            $scope.changeToSelectSpecType = function () {
                $scope.destroySpecTypeElement = false;
                $scope.assetSpecificationTypeSelect = true;
                loadAllAssetSpecificationTypeFields($scope.asset.assetSpecificationTypeId);
            };

            function loadAllAssetSpecificationTypeFields(assetSpecificationTypeId) {
                if (!angular.isUndefinedOrNull(assetSpecificationTypeId)) {
                    if (!angular.isUndefinedOrNull($scope.asset.id)) {
                        AssetSpecificationTypeValue.query({id: $scope.asset.id}, function (data) {
                            $scope.assetSpecificationTypeValues = data;
                        }, onError);
                    }

                    AssetSpecificationTypeFieldByType.query({id: assetSpecificationTypeId}, function (data) {
                        $scope.assetSpecificationTypeFields = data;
                        // $scope.destroySpecTypeElement = true;
                        addTypeFieldElements();
                    }, onError);
                }
            }

            function addTypeFieldElements() {
                var specTypeFormDir = document.querySelector(".asset-type-container");
                angular.element(specTypeFormDir).html("");
                // $scope.destroySpecTypeElement = false;
                angular.element(specTypeFormDir).append($compile("<asset-type-field-form asset=\"asset\" asset-type-fields=\"assetSpecificationTypeFields\" asset-type-values=\"assetSpecificationTypeValues\" destroy-element=\"destroySpecTypeElement\"></asset-type-field-form>")($scope));
            }

            function loadAllAssetSpecificationTypes() {
                AssetSpecificationType.query({}, function (data) {
                    $scope.assetSpecificationTypes = data;
                }, onError);
            }

            function loadAllManufactures() {
                AssetManufacture.query({}, onSuccessManufacture, onError);
            }

            function loadAllUsers() {
                User.query({}, onSuccess, onError);
            }


            function findAssetSpecificationTypeByFilter(field, value) {
                var example = {};
                example[field] = value;
                var assetSpecType = $filter('filter')($scope.assetSpecificationTypes, example, true);
                if (assetSpecType.length == 1) {
                    return assetSpecType;
                }
                return null;
            }

            function onSuccessManufacture(data) {
                $scope.manufactures = data;
            }

            function onSuccess(data) {
                for (var i = 0; i < data.length; i++) {
                    if (angular.isUndefinedOrNull(data[i].firstName) && angular.isUndefinedOrNull(data[i].lastName)) {
                        data[i].name = data[i].login;
                    }
                    else {
                        data[i].name = data[i].firstName + ' ' + data[i].lastName;
                    }
                }
                $scope.users = data;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }


            $scope.$watch('asset.assetSpecificationTypeId', function (newValue, oldValue) {
                loadAllAssetSpecificationTypeFields(newValue);
            });

            if (!angular.isUndefinedOrNull($scope.displayDetails)) {
                $scope.disableForm = true;
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


            $scope.mapsDropzoneConfig = {
                'options': { // passed into the Dropzone constructor
                    'url': '/api/upload-files'
                },
                acceptedFiles: "image/*,application/pdf",
                'eventHandlers': {
                    'success': function (file, response) {
                        if (!angular.isArray($scope.asset.maps)) {
                            $scope.asset.maps = [];
                        }
                        $scope.asset.maps.push(response);
                    }
                }
            };
            $scope.otherDropzoneConfig = {
                'options': { // passed into the Dropzone constructor
                    'url': '/api/upload-files'
                },
                'eventHandlers': {
                    'success': function (file, response) {
                        if (!angular.isArray($scope.asset.otherFiles)) {
                            $scope.asset.otherFiles = [];
                        }
                        $scope.asset.otherFiles.push(response);
                    }
                }
            };

        },
        link: function (scope, rootScope, element, attrs, compile) {


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
