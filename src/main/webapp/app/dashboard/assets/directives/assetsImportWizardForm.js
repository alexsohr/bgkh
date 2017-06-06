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
            var vm = this;
            var wizard = angular.element($element[0].getElementsByClassName('asset-import-wizard'));
            var forms = [];
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
                    var index = $scope.assetImportData.length;
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
                    pane = $compile('<assets-form update-asset-callback="getAsset(index)" data-parent-id="' + $scope.branch.id + '" data-parent-step="' + $scope.parentStep + '" data-current-step="' + index + '" step-change="addStep(value)" is-valid-call-back="isFormValid(valid)"></assets-form>')($scope);
                }
                else {
                    $scope.parentId = branch.id;
                    var newIndex = index - 1;
                    pane = $compile('<assets-form update-asset-callback="getAsset(index)" data-parent-id="' + $scope.branch.id + '" data-parent-step="' + $scope.parentStep + '" data-current-step="' + index + '" step-change="addStep(value)" is-valid-call-back="isFormValid(valid)" asset="wizardStepFormBranch[' + newIndex + ']" ></assets-form>')($scope);
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

            $scope.isFormValid = function (valid) {
                forms[valid[1]] = valid[0];
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
                        strategic: "false",
                        supervisorId: null,
                        technicianId: null,
                        year: null,
                        id: null
                    };
                }
                return $scope.assetList[index];
            }

            function assetWizardStepRemove(index, howMany) {
                wizard.wizard('removeSteps', index, howMany);
                for (var i = 0; i < howMany - 1; i++) {
                    $scope.assetImportData.pop();
                }
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
                evt.preventDefault();
                if (forms[forms.length - 1].$valid) {
                    evt.preventDefault();
                    console.log("Form finished");
                    $('.asset-import-form', $(evt.currentTarget)).triggerHandler('submit');
                }
            });


            wizard.on('changed.fu.wizard', function (evt, data) {
                if (data.step > 1 && data.direction === 'next' && !forms[data.step].$valid) {
                    console.log(forms[data.step]);
                    evt.preventDefault();
                }
            });
            wizard.on('actionclicked.fu.wizard', function (evt, data) {
                if (data.step > 1) {
                    console.log(forms[data.step]);
                    console.log("form step " +data.step + " validation is " + forms[data.step].$valid);
                }
                if (data.step > 1 && data.direction === 'next' && !forms[data.step].$valid) {
                    evt.preventDefault();
                }
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
