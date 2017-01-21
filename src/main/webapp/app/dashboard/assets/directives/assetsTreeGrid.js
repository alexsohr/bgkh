"use strict";

angular.module('app').directive('assetsTreeGrid', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'app/dashboard/assets/directives/assets-tree-grid.tpl.html',
        scope: true,
        controller: function ($state, $scope, $rootScope, $compile, $element, $sce, $templateCache, AssetImportModalService) {
            var tree;
            $scope.assets_tree = tree = {};
            $scope.tree_data = {};
            $scope.col_defs = [];
            $scope.expanding_property = {
                field: "name",
                displayName: $rootScope.getWord('Asset Name'),
                sortable: true,
                filterable: true
            };
            $scope.col_defs = [
                {field: "code", displayName: $rootScope.getWord('Asset Code'), sortable: true, filterable: true},
                {field: "type", displayName: $rootScope.getWord('Asset Type')},
                {field: "supervisor", displayName: $rootScope.getWord('Asset Supervisor')},
                {field: "capacity", displayName: $rootScope.getWord('Asset Capacity')},
                {field: "user", displayName: $rootScope.getWord('Asset User')},
                {
                    field: "demographicId",
                    displayName: $rootScope.getWord('Action'),
                    cellTemplate: "<div ng-hide=\"{{ row.branch['demographicId'] == 'root' }}\" class=\"btn-group dropdown\" data-dropdown>" +
                    "<button class=\"btn btn-primary btn-xs dropdown-toggle\" data-toggle=\"dropdown\">" +
                    $rootScope.getWord('Action') + " <span class=\"caret\"></span>" +
                    "</button>" +
                    "<ul class=\"dropdown-menu\">" +
                    "<li>" +
                    "<a href=\"#pop\" ng-click='cellTemplateScope.openAssetEditModal(row.branch)' ng-src='{{ row.branch[col.field] }}' data-target=\"#assetModal\" data-toggle=\"modal\">" + $rootScope.getWord('Edit') + "</a>" +
                    "</li>" +
                    "<li>" +
                    "<a href=\"#pop\" ng-click='cellTemplateScope.openAssetViewModal(row.branch)' ng-src='{{ row.branch[col.field] }}' data-target=\"#assetModal\" data-toggle=\"modal\">" + $rootScope.getWord('Details') + "</a>" +
                    "</li>" +
                    "<li>" +
                    "<a href=\"#pop\" ng-click='cellTemplateScope.openAssetDuplicateModal(row.branch)' ng-src='{{ row.branch[col.field] }}' data-target=\"#assetModal\" data-toggle=\"modal\">" + $rootScope.getWord('Duplicate') + "</a>" +
                    "</li>" +
                    "<li>" +
                    "<a href=\"#pop\" ng-click='cellTemplateScope.openAssetMoveModal(row.branch)' ng-src='{{ row.branch[col.field] }}' data-target=\"#assetModal\" data-toggle=\"modal\">" + $rootScope.getWord('Move') + "</a>" +
                    "</li>" +
                    "</ul>" +
                    "</div>",
                    cellTemplateScope: {
                        //FIXME cellTemplateScope.submitDataWithSuccessAlert() is not working
                        openAssetEditModal: function (branch) {
                            $scope.branch = branch;
                            console.log({"openAssetEditModal called": branch});
                            $scope.assetImportHeader = $rootScope.getWord('Edit Assets');
                            $scope.assetImportBody = $compile('<assets-form asset="branch"></assets-form>')($scope);
                            $scope.assetImportFooter = $sce.trustAsHtml("<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\" ng-click='cellTemplateScope.submitDataWithSuccessAlert()'>" + $rootScope.getWord('Save') + "</button>");
                        },
                        openAssetViewModal: function (branch) {
                            $scope.branch = branch;
                            console.log({"openAssetViewModal called": branch});
                            $scope.assetImportHeader = $rootScope.getWord('View Assets');
                            ;
                            $scope.assetImportBody = $compile('<assets-form asset="branch" display-details="true"></assets-form>')($scope);
                            $scope.assetImportFooter = '';
                        },
                        openAssetDuplicateModal: function (branch) {
                            $scope.branch = branch;
                            console.log("openAssetImportModal called!");
                            $scope.assetImportHeader = $rootScope.getWord('Duplicate Assets');
                            $scope.assetImportBody = $compile('<assets-import-wizard callback-Wizard-Finish="assetImportModalClose()" asset="branch" duplicate-asset="True"></assets-import-wizard>')($scope);
                            $scope.assetImportFooter = "";
                        },
                        openAssetMoveModal: function (branch) {
                            $scope.branch = branch;
                            var treeTemplate = $templateCache.get('app/dashboard/assets/directives/assetTreeModalContent.tpl.html');
                            console.log("openAssetMoveModal called!");
                            $scope.assetImportHeader = $rootScope.getWord('Move Asset');
                            $scope.assetImportBody = $compile(treeTemplate)($scope);
                            $scope.assetImportFooter = $sce.trustAsHtml("<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\" ng-click='cellTemplateScope.submitDataWithSuccessAlert()'>" + $rootScope.getWord('Save') + "</button>");
                        },
                        submitDataWithSuccessAlert: function () {
                            console.log("submitDataWithSuccessAlert")
                            $.smallBox({
                                title: $rootScope.getWord("Data submitted successfully") + "!",
                                content: "<i class='fa fa-clock-o'></i> <i>" + $rootScope.getWord('1 second ago') + "...</i>",
                                color: "#5F895F",
                                iconSmall: "fa fa-check bounce animated",
                                timeout: 4000
                            });
                        }
                    }
                }
            ];
            $scope.tree_data = $rootScope.tree_data;

            $scope.assetImportModalClose = function () {
                console.log("Asset modal closed");
                $scope.assets_tree.select_branch(null);
                if(AssetImportModalService.isOpen()) {
                    AssetImportModalService.close();
                }
            }

            $scope.openAssetImportModal = function () {
                if(!AssetImportModalService.isOpen()) {
                    AssetImportModalService.open();
                }
                console.log("openAssetImportModal called!");
            }

        },
        link: function (scope, element, attrs) {
        }
    }
});
