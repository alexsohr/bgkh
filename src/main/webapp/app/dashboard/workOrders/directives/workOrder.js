"use strict";

angular.module('app').directive('workOrder', function () {
    return {
        restrict: 'E',
        replace: true,
        required: '^workOrdersAssetsTreeGrid',
        transclude: true,
        scope: {
            workOrders: '=workOrders',
            nested: '@nested'
        },
        templateUrl: 'app/dashboard/workOrders/directives/work-order.tpl.html',
        controller: function ($scope, $rootScope, $compile, $sce, $element, $ctrl) {
            $ctrl.
            $scope.workOrderModalClass = "work-order-modal";
            var workOrderData = $scope.workOrders.data;
            $scope.workOrdersTableOptions = {
                "data": workOrderData,
//            "bDestroy": true,
                "iDisplayLength": 5,
                "columns": [
                    {
                        "class": 'details-control',
                        "orderable": false,
                        "data": null,
                        "defaultContent": ''
                    },
                    {"data": "id"},
                    {"data": "assets"},
                    {"data": "type"},
                    {"data": "status"},
                    {"data": "est"},
                    {"data": "priority"},
                    {"data": "estimated-hours"},
                    {
                        "data": "tracker",
                        "orderable": false,
                        "render": function (data, type, row) {
                            return "<span class='onoffswitch'><input type='checkbox' name='start_interval' class='onoffswitch-checkbox' id='st" + row.id + "' " + (row.tracker ? 'checked=\'checked\'' : '') + " value='" + row.id + "'><label class='onoffswitch-label' for='st" + row.id + "'><span class='onoffswitch-inner' data-swchon-text='روشن' data-swchoff-text='خاموش'></span><span class='onoffswitch-switch'></span></label></span>"
                        }
                    },
                    {"data": "users"},
                    {
                        "data": "id",
                        "orderable": false,
                        // The `data` parameter refers to the data for the cell (defined by the
                        // `data` option, which defaults to the column being worked with, in
                        "render": function (data, type, row) {

                            return "<div class=\"btn-group dropdown\" data-dropdown>" +
                                "<button class=\"btn btn-primary btn-xs dropdown-toggle\" data-toggle=\"dropdown\">" +
                                $rootScope.getWord('Action') + " <span class=\"caret\"></span>" +
                                "</button>" +
                                "<ul class=\"dropdown-menu\">" +
                                "<li>" +
                                "<a href=\"#pop\" ng-click='openWorkOrderEditModal(" + row.id + ")' data-target=\"#workOrderModal\" data-toggle=\"modal\">" + $rootScope.getWord('Edit') + "</a>" +
                                "</li>" +
                                "<li>" +
                                "<a href=\"#pop\" ng-click='openWorkOrderViewModal(row)' ng-src='{{ row }}' data-target=\"#workOrderModal\" data-toggle=\"modal\">" + $rootScope.getWord('Details') + "</a>" +
                                "</li>" +
                                "<li>" +
                                "<a href=\"#pop\" ng-click='openWorkOrderDuplicateModal(row)' ng-src='{{ row }}' data-target=\"#workOrderModal\" data-toggle=\"modal\">" + $rootScope.getWord('Duplicate') + "</a>" +
                                "</li>" +
                                "</ul>" +
                                "</div>";
                        }
                    }
                ],
                "order": [[1, 'asc']]
            };

            $scope.workOrderEditModalClose = function () {
                $scope.assets_tree.select_branch(null);
                $element.find('.modal').modal('hide');
                var header = "";
                var body = "<div></div>";
                var footer = "";
                setFormData(header, body, footer);
            };

            $scope.submitDataWithSuccessAlert = function () {
                $.smallBox({
                    title: $rootScope.getWord("Data submitted successfully") + "!",
                    content: "<i class='fa fa-clock-o'></i> <i>" + $rootScope.getWord('1 second ago') + "...</i>",
                    color: "#5F895F",
                    iconSmall: "fa fa-check bounce animated",
                    timeout: 4000
                });
            };

            $scope.openWorkOrderEditModal = function (data) {
                $scope.workOrderRow = getRowDataById(data);
                var header = $rootScope.getWord('Edit Work order');
                var body = $compile('<work-order-form work-order="workOrderRow"></work-order-form>')($scope);
                var footer = $sce.trustAsHtml("<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\" ng-click='submitDataWithSuccessAlert()'>" + $rootScope.getWord('Save') + "</button>");
                setFormData(header, body, footer);
            };
            $scope.openWorkOrderDuplicateModal = function (data) {
                $scope.workOrderRow = getRowDataById(data);
                var header = $rootScope.getWord('Duplicated Work order');
                var body = $compile('<work-order-form work-order="workOrderRow"></work-order-form>')($scope);
                var footer = $sce.trustAsHtml("<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\" ng-click='submitDataWithSuccessAlert()'>" + $rootScope.getWord('Save') + "</button>");
                setFormData(header, body, footer);
            };
            $scope.openWorkOrderViewModal = function (data) {
                $scope.workOrderRow = getRowDataById(data);
                var header = $rootScope.getWord('View Work order');
                var body = $compile('<work-order-form work-order="workOrderRow" display-details="true"></work-order-form>')($scope);
                var footer = "";
                setFormData(header, body, footer);
            };
            // $scope.openWorkOrderAddModal = function () {
            //     console.log("Opening work order modal");
            //     if (WorkOrderAddModalService.isOpen()) {
            //         WorkOrderAddModalService.open();
            //     }
                // var header = $rootScope.getWord('Add Work order');
                // var body = $compile('<work-order-form></work-order-form>')($scope);
                // var footer = $sce.trustAsHtml("<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\" ng-click='submitDataWithSuccessAlert()'>" + $rootScope.getWord('Save') + "</button>");
                // setFormData(header, body, footer);
            // }

            function setFormData(header, body, footer) {
                console.log("Setting form data");
                $scope.workOrderImportHeader = header;
                $scope.workOrderImportBody = body;
                $scope.workOrderImportFooter = footer;
            }

            function getRowDataById(id) {
                var rowData = null;
                angular.forEach(workOrderData, function (row) {
                    if (row.id == id) {
                        rowData = row;
                    }
                });
                return rowData;
            }
        },
        link: function (scope, rootScope, element, attrs) {
        }
    }
});
