"use strict";

angular.module('app').directive('assetsImportWizardForm', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'app/dashboard/assets/directives/asset-import-wizard-form.tpl.html',
        scope: {
            'onSubmit': '&'
        },
        controller: function ($scope, $compile, $rootScope, $element) {
            $scope.wizardStepFormBranch = [];
            $scope.assetList = [];
            $scope.duplicate = false;
            $scope.selectTreeElement = "root";
            $scope.duplicatedAsset;
            if ($scope.duplicateAsset) {
                $scope.duplicate = true;
            }
            $scope.$watch('asset', function (value) {
                if (!angular.isUndefined(value)) {
                    $scope.duplicatedAsset = value;
                    $scope.wizardStepFormBranch = tree2line(value);
                    for (var i = 0; i < $scope.wizardStepFormBranch.length; i++) {
                        assetWizardStepAdd(i + 2, $scope.wizardStepFormBranch[i]);
                    }
                }
            });
            // var wizard = {};
            var wizard = $element.children().first();
            $scope.assetImportData = [];
            $scope.branch;
            $scope.parentId = null;
            $scope.parentStep = null;
            $scope.assetId = null;
            $scope.assetImportData[0] = {"step": 1};
            $scope.selectedParentTree = function (branch) {
                var index = wizard.wizard('selectedItem');
                $scope.branch = branch;
                $scope.assetId = branch.demographicId;
                if ($scope.assetImportData.length < 2) {
                    $scope.assetImportData[0].data = {"assetId": $scope.assetId};
                    $scope.assetImportData[0].hasNext = true;
                    var index = 2;
                    assetWizardStepAdd(index);
                }
            };

            $scope.addStep = function (value) {
                if (value && !angular.isUndefined(value)) {
                    var index = $scope.assetImportData.length + 1;
                    assetWizardStepAdd(index);
                }
                else if (angular.isUndefined(value)) {
                }
                else {
                    var index = wizard.wizard('selectedItem');
                    var indexToRemove = index.step + 1;
                    var howMany = $scope.assetImportData.length - index.step;
                    assetWizardStepRemove(indexToRemove, howMany);
                }
            };
            function assetWizardStepAdd(index) {
                assetWizardStepAdd(index, null);
            }

            function assetWizardStepAdd(index, branch) {
                $scope.assetImportData[index] = {
                    "step": index,
                    "hasNext": true,
                    "data": {"assetId": $scope.assetId}
                };
                $scope.assetList[index] = {};

                var pane;
                if (!angular.isObject(branch)) {
                    pane = $compile('<assets-form update-asset-callback="getAsset(index)" data-parent-id="'+$scope.branch.id+'" data-parent-step="'+$scope.parentStep+'" data-current-step="'+index+'" step-change="addStep(value)"></assets-form>')($scope);
                }
                else {
                    $scope.parentId = branch.id;
                    var newIndex = index - 1;
                    pane = $compile('<assets-form update-asset-callback="getAsset(index)" data-parent-id="'+$scope.branch.id+'" data-parent-step="'+$scope.parentStep+'" data-current-step="'+index+'" step-change="addStep(value)" asset="wizardStepFormBranch[' + newIndex + ']" ></assets-form>')($scope);
                }

                wizard.wizard('addSteps', index, [
                    {
                        badge: index,
                        label: '',
                        pane: pane
                    }
                ]);
                $scope.parentStep = index;
            }

            $scope.getAsset = function (index) {
                console.log("index return: " + index);
                if (!angular.isObject($scope.assetList[index])) {
                    $scope.assetList[index] = {
                        parentId: null,
                        name: null,
                        code: null,
                        assetType: null,
                        capacity: null,
                        manufacture: null,
                        supervisorId: null,
                        technicianId: null,
                        typeVal: null,
                        year: null,
                        id: null
                    };
                }
                return $scope.assetList[index];
            }

            function assetWizardStepRemove(index, howMany) {
                wizard.wizard('removeSteps', index, howMany);

            }

            function tree2line(branch) {
                var data = [];

                function recurse(current) {
                    data[data.length] = current;
                    if (Array.isArray(current.children)) {
                        for (var i = 0; i < current.children.length; i++) {
                            recurse(current.children[i]);
                        }
                    }
                }

                recurse(branch);
                return data;
            }

            wizard.on('finished.fu.wizard', function (evt, data) {
                console.log("Form finished");

                $('.asset-import-form', $(evt.currentTarget)).triggerHandler('submit');
            });

            $scope.wizard2CompleteCallback = function (wizardData) {

                var data = {parentId: $scope.branch.id, assetList: $scope.assetList};
                $scope.onSubmit({data: data});
                console.log(data);

                if ($scope.callbackWizardFinish) {
                    $scope.callbackWizardFinish(data);
                }
                $.smallBox({
                    title: $rootScope.getWord("Data submitted successfully!"),
                    content: "<i class='fa fa-clock-o'></i> <i>" + $rootScope.getWord('1 second ago') + "...</i>",
                    color: "#5F895F",
                    iconSmall: "fa fa-check bounce animated",
                    timeout: 4000
                });
                $scope.$destroy();
            }
        },
        link: function (scope, rootScope, element, attrs) {

        }
    }
});
