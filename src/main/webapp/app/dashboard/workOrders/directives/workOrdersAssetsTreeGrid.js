"use strict";

angular.module('app').directive('workOrdersAssetsTreeGrid', function () {
    return {
        restrict: 'E',
        replace: true,
        transclude: true,
        templateUrl: 'app/dashboard/workOrders/directives/work-order-assets-tree-grid.tpl.html',
        scope: true,
        controllerAs: 'workOrdersAssetsTreeGrid',
        controller: function ($scope, $rootScope, $compile, $element, $sce, $templateCache, AssetSpecificationType, WorkOrderTemplate, Asset, WorkOrderAddModalService) {
            var vm = this;
            $scope.assetSpecificationTypes = [];
            $scope.workOrderTemplates = [];
            var tree;
            $scope.work_order_assets_tree = tree = {};
            $scope.tree_data = {};

            $scope.loadWorkOrders = loadWorkOrders;
            $scope.loadWorkOrders();

            function loadWorkOrders() {
                AssetSpecificationType.query(function (assetSpecificationTypes) {
                    WorkOrderTemplate.query(function (workOrderTemplates) {
                        $scope.workOrders = getTree(assetSpecificationTypes, workOrderTemplates);
                        $scope.tree_data = angular.copy($scope.workOrders);
                        $scope.searchQuery = null;
                        tree.expand_all();
                    });
                });
            }

            $rootScope.$on('appApp:workOrderTemplateUpdate', function(event, result) {
                $scope.loadWorkOrders();
            });

            $scope.expanding_property = {
                field: "name",
                displayName: $rootScope.getWord('Name'),
                sortable: true,
                filterable: true,
               // cellTemplate: " {{row.branch['name']}} <span class=\"badge\">{{row.branch['count']}}</span>"
            };
            $scope.col_defs = [
                {field: "numberOfDays", displayName: $rootScope.getWord('Number of days')},
                {field: "hoursOfUsage", displayName: $rootScope.getWord('Number of hours')},
                {field: "dueDays", displayName: $rootScope.getWord('Due day')},
                {field: "workOrderType", displayName: $rootScope.getWord('Work order type')},
                {field: "functionType", displayName: $rootScope.getWord('Work order function')},
                {
                    field: "demographicId",
                    displayName: $rootScope.getWord('Action'),
                    cellTemplate: "<div ng-hide=\"{{ row.branch['demographicId'].indexOf('parent_') == 0 }}\" class=\"btn-group dropdown\" data-dropdown>" +
                    // cellTemplate: "<div class=\"btn-group dropdown\" data-dropdown>" +
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
                    "<a ng-click='cellTemplateScope.deleteConfirm(row.branch.id)' >" + $rootScope.getWord('Delete') + "</a>" +
                    "</li>" +
                    "</ul>" +
                    "</div>",
                    cellTemplateScope: {
                        deleteConfirm: function (id) {
                            if (!$scope.deleteCalled) {
                                $.SmartMessageBox({
                                    title: $rootScope.getWord("Alert!"),
                                    content: $rootScope.getWord("Are you sure to delete this Work order template?"),
                                    buttons: '[No][Yes]'
                                }, function (ButtonPressed) {
                                    if (ButtonPressed === "Yes") {
                                        WorkOrderTemplate.delete({id: id}).$promise.then(function (result) {
                                            $.smallBox({
                                                title: $rootScope.getWord("Notification"),
                                                content: "<i class='fa fa-clock-o'></i> <i>" + $rootScope.getWord('Work order template deleted!') + "</i>",
                                                color: "#659265",
                                                iconSmall: "fa fa-check fa-2x fadeInRight animated",
                                                timeout: 4000
                                            });
                                            $scope.$emit('appApp:workOrderTemplateUpdate', result);
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
                            if (!WorkOrderAddModalService.isOpen()) {
                                WorkOrderAddModalService.openEdit(branchId);
                            }
                        },
                        openAssetViewModal: function (branchId) {
                            if (!WorkOrderAddModalService.isOpen()) {
                                WorkOrderAddModalService.openDetails(branchId);
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

            $scope.init = function () {
                $scope.workOrderAssetImportHeader = "";
                $scope.workOrderAssetImportBody = "<div></div>";
                $scope.workOrderAssetImportFooter = "";
            }

            $scope.openWorkOrderAddModal = function () {
                console.log("Opening work order modal");
                if (!WorkOrderAddModalService.isOpen()) {
                    WorkOrderAddModalService.openAdd();
                }
            }

            function getTree(assetSpecificationTypes, workOrderTemplates) {
                var tree = [], i = 0;
                if (assetSpecificationTypes.length > 0) {
                    assetSpecificationTypes.forEach(function (assetSpecificationType) {
                        tree[i] = {};
                        tree[i].id = assetSpecificationType.id;
                        tree[i].demographicId = "parent_" + assetSpecificationType.id;
                        tree[i].children = [];
                        var j = 0
                        if (workOrderTemplates.length > 0) {
                            workOrderTemplates.forEach(function (workOrderTemplate) {
                                var data = workOrderTemplate;
                                if (workOrderTemplate.assetSpecificationTypeId == assetSpecificationType.id) {
                                    var child = data;
                                    child.demographicId = child.id;
                                    tree[i].children.push(child);
                                    j++;
                                }
                            });
                        }
                        tree[i].count = j;
                        tree[i].name = assetSpecificationType.name;
                        i++;
                    });
                }
                console.dir(tree);
                return tree;
            }

        },
        link: function (scope, element, attrs) {
            scope.init();
        }
    }
});
