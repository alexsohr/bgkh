"use strict";

angular.module('app').directive('assetsTreeGridCheckbox', function () {
    return {
        restrict: 'E',
        replace: true,
        templateUrl: 'app/dashboard/assets/directives/assets-tree-grid-checkbox.tpl.html',
        scope: {
            selectedTreeNode: '=selectedTreeNode',
            onSelect: '&'
        },
        controller: function ($scope, $rootScope, Asset) {
            var tree_check;

            $scope.assets_tree_check = tree_check = {};

            $scope.tree_data_check = {};
            $scope.col_defs_check = [];
            $scope.expanding_property_check = {
                field: "name",
                displayName: $rootScope.getWord('Asset Name'),
                sortable: true,
                filterable: true
            };
            $scope.col_defs_check = [];

            $scope.loadAssets = loadAssets;
            $scope.loadAssets();

            function loadAssets() {
                Asset.query(function (result) {
                    $scope.tree_data_check = getTree(result, 'id', 'parentId');
                    $scope.searchQuery = null;
                });
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
                console.log("tree" + tree);
                return tree;
            }


            $scope.$watch('selectedTreeNode', function (branch) {
                if (angular.isObject(branch)) {
                console.log({selectedTreeNode: branch});
                    var parentBranch = $scope.assets_tree_check.select_parent_branch(branch);
                    $scope.assets_tree_check.expand_branch(parentBranch);
                }
                else {
                    console.log($scope.assets_tree_check.select_branch(false));
                }
            });
            $scope.assetsTreeCheckboxClick = function (branch) {
                if ($scope.onSelect) {
                    $scope.onSelect({
                        branch: branch
                    });
                }
            };
        },
        link: function (scope, element, attrs) {
        }
    }
});
