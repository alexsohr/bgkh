"use strict";

angular.module('app').directive('assetsTreeGrid', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'app/dashboard/assets/directives/assets-tree-grid.tpl.html',
        scope: {},
        controller: function ($state, $scope, $rootScope, $compile, $element, $sce, $templateCache, AssetImportModalService, Asset) {
            var tree;
            $scope.assets_tree = tree = {};
            $scope.tree_data = {};
            $scope.col_defs = [];
            $scope.loadAssets = loadAssets;
            $scope.loadAssets();
            $rootScope.$on('app:assetUpdate', function (event, result) {
                $scope.loadAssets();
            });
            $scope.expanding_property = {
                field: "name",
                displayName: $rootScope.getWord('Asset Name'),
                sortable: true,
                filterable: true
            };
            $scope.col_defs = [
                {field: "code", displayName: $rootScope.getWord('Asset Code'), sortable: true, filterable: true},
                {field: "assetType", displayName: $rootScope.getWord('Asset Type')},
                {field: "capacity", displayName: $rootScope.getWord('Asset Capacity')},
                {
                    field: "supervisor", displayName: $rootScope.getWord('Asset Supervisor'),
                    cellTemplate: "<span>{{row.branch.supervisorFirstName}} {{row.branch.supervisorLastName}}</span>"
                },
                {
                    field: "technician", displayName: $rootScope.getWord('Asset User'),
                    cellTemplate: "<span>{{row.branch.technicianFirstName}} {{row.branch.technicianLastName}}</span>"
                },
                {
                    field: "demographicId",
                    displayName: $rootScope.getWord('Action'),
                    cellTemplate: "<div ng-hide=\"{{ row.branch['code'] == 'ROOT' }}\" class=\"btn-group dropdown\" data-dropdown>" +
                    "<button class=\"btn btn-primary btn-xs dropdown-toggle\" data-toggle=\"dropdown\">" +
                    $rootScope.getWord('Action') + " <span class=\"caret\"></span>" +
                    "</button>" +
                    "<ul class=\"dropdown-menu\">" +
                    "<li>" +
                    "<a ng-click='cellTemplateScope.openAssetEditModal(row.branch.id)' >" + $rootScope.getWord('Edit') + "</a>" +
                    "</li>" +
                    "<li>" +
                    "<a ng-click='cellTemplateScope.openAssetViewModal(row.branch.id)' >" + $rootScope.getWord('Details') + "</a>" +
                    "</li>" +
                    "<li>" +
                    "<a ng-click='cellTemplateScope.openAssetDuplicateModal(row.branch.id)' >" + $rootScope.getWord('Duplicate') + "</a>" +
                    "</li>" +
                    "<li>" +
                    "<a ng-click='cellTemplateScope.openAssetMoveModal(row.branch.id)' >" + $rootScope.getWord('Move') + "</a>" +
                    "</li>" +
                    "<li>" +
                    "<a ng-click='cellTemplateScope.deleteConfirm(row.branch.id)' >" + $rootScope.getWord('Delete') + "</a>" +
                    "</li>" +
                    "</ul>" +
                    "</div>",
                    cellTemplateScope: {
                        //FIXME cellTemplateScope.submitDataWithSuccessAlert() is not working
                        deleteConfirm: function (id) {
                            if (!$scope.deleteCalled) {
                                $.SmartMessageBox({
                                    title: $rootScope.getWord("Alert!"),
                                    content: $rootScope.getWord("Are you sure to delete this Asset?"),
                                    buttons: '[No][Yes]'
                                }, function (ButtonPressed) {
                                    if (ButtonPressed === "Yes") {
                                        Asset.delete({id: id}).$promise.then(function (result) {
                                            $.smallBox({
                                                title: $rootScope.getWord("Notification"),
                                                content: "<i class='fa fa-clock-o'></i> <i>" + $rootScope.getWord('Asset deleted!') + "</i>",
                                                color: "#659265",
                                                iconSmall: "fa fa-check fa-2x fadeInRight animated",
                                                timeout: 4000
                                            });
                                            $scope.$emit('app:assetUpdate', result);
                                        }, function (msg) {
                                            console.error(msg);
                                            $.smallBox({
                                                title: $rootScope.getWord("Notification"),
                                                content: "<i class='fa fa-clock-o'></i> <i>" + msg + "</i>",
                                                color: "#C46A69",
                                                iconSmall: "fa fa-check fa-2x fadeInRight animated",
                                                timeout: 4000
                                            });
                                        });
                                    }
                                    $scope.deleteCalled = false;
                                });
                                $scope.deleteCalled = true;
                            }
                        },
                        openAssetEditModal: function (branchId) {
                            if (!AssetImportModalService.isOpen()) {
                                AssetImportModalService.openEdit(branchId);
                            }
                        },
                        openAssetViewModal: function (branchId) {
                            if (!AssetImportModalService.isOpen()) {
                                AssetImportModalService.openDetails(branchId);
                            }
                        },
                        openAssetDuplicateModal: function (branchId) {
                            if (!AssetImportModalService.isOpen()) {
                                AssetImportModalService.openCopy(branchId);
                            }
                        },
                        openAssetMoveModal: function (branchId) {
                            if (!AssetImportModalService.isOpen()) {
                                AssetImportModalService.openMove(branchId);
                            }
                        },
                        submitDataWithSuccessAlert: function () {
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

            function loadAssets() {
                Asset.query(function (result) {
                    $scope.tree_data = getTree(result, 'id', 'parentId');
                    $scope.searchQuery = null;
                });
            }

            $scope.assetImportModalClose = function () {
                console.log("Asset modal closed");
                $scope.assets_tree.select_branch(null);
                if (AssetImportModalService.isOpen()) {
                    AssetImportModalService.close();
                }
            }

            $scope.openAssetImportModal = function () {
                if (!AssetImportModalService.isOpen()) {
                    AssetImportModalService.open();
                }
                console.log("openAssetImportModal called!");
            }

            function getTree(data, primaryIdName, parentIdName) {
                if (!data || data.length == 0 || !primaryIdName || !parentIdName)
                    return [];

                var tree = [],
                    rootIds = [],
                    item = data[0],
                    primaryKey = item[primaryIdName],
                    treeObjs = {},
                    parentId,
                    parent,
                    len = data.length,
                    i = 0,
                    primeryIds = [];

                while (i < len) {
                    item = data[i++];
                    primaryKey = item[primaryIdName];
                    if (!primeryIds[primaryKey]) {
                        treeObjs[primaryKey] = item;
                        parentId = item[parentIdName];

                        if (parentId) {
                            parent = treeObjs[parentId];

                            if (parent.children !== undefined) {
                                parent.children.push(item);
                            }
                            else {
                                parent.children = [item];
                            }
                        }
                        else {
                            rootIds.push(primaryKey);
                        }
                    }
                    primeryIds.push(primaryKey)
                }

                for (var i = 0; i < rootIds.length; i++) {
                    tree.push(treeObjs[rootIds[i]]);
                }
                ;

                return tree;
            }

        },
        link: function (scope, element, attrs) {
        }
    }
});
