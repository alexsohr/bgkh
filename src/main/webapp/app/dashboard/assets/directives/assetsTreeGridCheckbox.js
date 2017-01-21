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
        controller: function ($scope, $rootScope) {
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

            $scope.tree_data_check = angular.copy($rootScope.tree_data);
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
