"use strict";

angular.module('app').directive('workOrdersAssetsTreeGrid', function () {
    return {
        restrict: 'E',
        replace: true,
        transclude: true,
        templateUrl: 'app/dashboard/workOrders/directives/work-order-assets-tree-grid.tpl.html',
        scope: true,
        controllerAs: 'workOrdersAssetsTreeGrid',
        controller: function ($scope, $rootScope, $compile, $element, $sce, $templateCache, Asset) {
            var tree;
            $scope.work_order_assets_tree = tree = {};

            $scope.loadAssets = loadAssets;
            $scope.loadAssets();

            function loadAssets() {
                Asset.query(function (result) {
                    $scope.workOrders = getTree(result, 'id', 'parentId');
                    $scope.searchQuery = null;
                });
            }
            $scope.workOrders = $rootScope.workOrders;

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
                    cellTemplate: "<button ng-click=\"cellTemplateScope.openWorkOrderViewModal(row.branch)\" ng-show=\"{{ row.branch['formType'] == 2 || row.branch['formType'] == 1 }}\"  type=\"button\" class=\"btn btn-labeled btn-info\" ng-src='{{ row.branch[col.field] }}' data-target=\"#workOrderAssetModal\" data-toggle=\"modal\">" +
                    "<span class=\"btn-label\">" +
                    "<i class=\"fa fa-gear\"></i>" +
                    "</span>"+$rootScope.getWord('Work order')+"</button>",
                    cellTemplateScope: {
                        openWorkOrderViewModal: function (branch) {
                            console.log("openWorkOrderViewModal");
                            $scope.workOrderAssetImportHeader = $rootScope.getWord('Edit work orders');
                            $scope.workOrderAssetImportBody = $compile('<work-order work-orders="workOrders" nested="nested"></work-order>')($scope);
                            $scope.workOrderAssetImportFooter = "";
                        }
                    }
                }
            ];
            $scope.tree_data = angular.copy($rootScope.tree_data);


            $scope.init = function () {
                $scope.workOrderAssetImportHeader = "";
                $scope.workOrderAssetImportBody = "<div></div>";
                $scope.workOrderAssetImportFooter = "";
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

                            if (parent.children) {
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
            scope.init();
        }
    }
});
