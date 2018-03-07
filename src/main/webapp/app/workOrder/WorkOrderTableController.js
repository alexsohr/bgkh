(function () {
    'use strict';

    angular
        .module('app.workOrder')
        .controller('WorkOrderTableController', WorkOrderTableController);

    WorkOrderTableController.$inject = ['$rootScope', '$scope', '$resource', '$compile', 'WorkOrderScheduleByUser', 'DTOptionsBuilder', 'DTColumnBuilder', 'WorkOrderScheduleDetailModalService'];

    function WorkOrderTableController($rootScope, $scope, $resource, $compile, WorkOrderScheduleByUser, DTOptionsBuilder, DTColumnBuilder, WorkOrderScheduleDetailModalService) {
        var vm = this;
        vm.dtInstance = {};
        vm.workOrderSchedule = {};
        vm.dtInstanceCallback = function(_dtInstance){
            vm.dtInstance = _dtInstance;
        };
        vm.standardOptions = DTOptionsBuilder.fromFnPromise(function () {
            return WorkOrderScheduleByUser.query().$promise;
        })
            //Add Bootstrap compatibility
            .withDOM("<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-12 hidden-xs'l>r>" +
                "t" +
                "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>")
            .withBootstrap()
            .withLanguageSource('data/tables/persian.json')
            .withOption('createdRow', createdRow)
            .withOption('fnRowCallback', rowCallback);

        function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
            var rowClass = nRow.className;
            if (aData.scheduleStatus == 'IN_PROGRESS') {
                rowClass = nRow.className + " warning";
            }
            if (moment(aData.expireDate).isBefore(moment())) {
                rowClass = nRow.className + " danger";
            }
            if (aData.scheduleStatus == 'COMPLETED') {
                rowClass = nRow.className + " success";
            }

            nRow.className = rowClass;
            return nRow;
        }

        $rootScope.$on('app:workOrderScheduleUpdate', function (event, result) {
            // vm.dtInstance.changeData('api/work-order-schedules/');
            vm.dtInstance.changeData(function() {
                return $resource('api/work-order-schedules/').query().$promise;
            });
            console.log(vm);
            // vm.dtInstance.rerender();
        });

        vm.standardColumns = [
            DTColumnBuilder.newColumn('workOrderTemplate.name'),
            DTColumnBuilder.newColumn('workOrderTemplate.workOrderType'),
            DTColumnBuilder.newColumn('workOrderTemplate.functionType'),
            DTColumnBuilder.newColumn('scheduleStatus').renderWith(function (data, type, full) {
                var style = 'primary';
                if (full.scheduleStatus == 'IN_PROGRESS') {
                    style = 'warning';
                } else if (full.scheduleStatus == 'COMPLETED') {
                    style = 'success';
                }
                var data = $rootScope.getWord(full.scheduleStatus);
                return "<span class=\"label label-" + style + "\">"+data+"</span>";
            }),
            DTColumnBuilder.newColumn('workOrder.asset.name'),
            DTColumnBuilder.newColumn('workOrder.asset.code'),
            DTColumnBuilder.newColumn('createDate').renderWith(function (createDate) {
                return moment(createDate, 'YYYY-MM-DD').format('ll');
            }),
            DTColumnBuilder.newColumn('expireDate').renderWith(function (expireDate) {
                return moment(expireDate).fromNow();
            }),
            DTColumnBuilder.newColumn(null).renderWith(actionHtml).notSortable()
        ];

        function actionHtml(data, type, full, meta) {
            vm.workOrderSchedule[data.id] = data;
            var details = $rootScope.getWord('Details');
            var action = $rootScope.getWord('Action');
            return "<div class=\"btn-group dropdown\" data-dropdown>" +
                "<button class=\"btn btn-primary btn-xs dropdown-toggle\" data-toggle=\"dropdown\">" +
                action +" <span class=\"caret\"></span>" +
                "</button>" +
                "<ul class=\"dropdown-menu dropdown-menu-right pull-right\" aria-labelledby=\"dLabel\">" +
                "<li>" +
                "<a ng-click=\"vm.showDetail(vm.workOrderSchedule[" + data.id + "])\">"+details+"</a>" +
                "</li>" +
                "</ul>" +
                "</div>";
        }

        function createdRow(row, data, dataIndex) {
            // Recompiling so we can bind Angular directive to the DT
            $compile(angular.element(row).contents())($scope);
        }

        vm.showDetail = function (data) {
            if (!WorkOrderScheduleDetailModalService.isOpen()) {
                WorkOrderScheduleDetailModalService.openDetails(data.id);
            }
        }
    }
})();
