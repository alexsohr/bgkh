(function () {
    'use strict';

    angular
        .module('app.workOrder')
        .controller('WorkOrderTableController', WorkOrderTableController);

    WorkOrderTableController.$inject = ['$rootScope', '$scope', '$compile', '$uibModal', 'WorkOrderSchedule', 'DTOptionsBuilder', 'DTColumnBuilder', 'WorkOrderScheduleDetailModalService'];

    function WorkOrderTableController($rootScope, $scope, $compile, $uibModal, WorkOrderSchedule, DTOptionsBuilder, DTColumnBuilder, WorkOrderScheduleDetailModalService) {
        var vm = this;
        this.standardOptions = DTOptionsBuilder
            .fromSource('api/work-order-schedules/')
            //Add Bootstrap compatibility
            .withDOM("<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-12 hidden-xs'l>r>" +
                "t" +
                "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>")
            .withBootstrap()
            .withOption('fnRowCallback', rowCallback);

        function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
            return nRow;
        }

        this.standardColumns = [
            DTColumnBuilder.newColumn('workOrderTemplate.name'),
            DTColumnBuilder.newColumn('workOrderTemplate.workOrderType'),
            DTColumnBuilder.newColumn('workOrderTemplate.functionType'),
            DTColumnBuilder.newColumn('scheduleStatus').renderWith(function (scheduleStatus) {
                var style = 'primary';
                if (scheduleStatus == 'IN_PROGRESS') {
                    style = 'warning';
                } else if (scheduleStatus == 'COMPLETED') {
                    style = 'success';
                }
                return "<span class=\"label label-" + style + "\">"+scheduleStatus+"</span>";
            }),
            DTColumnBuilder.newColumn('workOrder.asset.name'),
            DTColumnBuilder.newColumn('workOrder.asset.code'),
            DTColumnBuilder.newColumn('createDate').renderWith(function (createDate) {
                return moment(createDate, 'YYYY-MM-DD').format('ll');
            }),
            DTColumnBuilder.newColumn('expireDate').renderWith(function (expireDate) {
                return moment(expireDate).fromNow();
            }),
            DTColumnBuilder.newColumn('id').renderWith(function (id) {

                return "<div class=\"btn-group dropdown\" data-dropdown>" +
                    "<button class=\"btn btn-primary btn-xs dropdown-toggle\" data-toggle=\"dropdown\">" +
                    "{{$root.getWord('Action')}}"+" <span class=\"caret\"></span>" +
                    "</button>" +
                    "<ul class=\"dropdown-menu dropdown-menu-right pull-right\" aria-labelledby=\"dLabel\">" +
                    "<li>" +
                    "<a ng-click='vm.showDetail(" + id + ")'>{{$root.getWord('Details')}}</a>" +
                    "</li>" +
                    "</ul>" +
                    "</div>";
            }).notSortable()
        ];

        this.showDetail = function (id) {
            if (!WorkOrderScheduleDetailModalService.isOpen()) {
                WorkOrderScheduleDetailModalService.openDetails(id);
            }
        }
    }
})();
