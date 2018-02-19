(function() {
    'use strict';
    angular
        .module(workOrderSwitchVm)
        .factory('WorkOrderSchedule', WorkOrderSchedule);

    WorkOrderSchedule.$inject = ['$resource', 'DateUtils'];

    function WorkOrderSchedule ($resource, DateUtils) {
        var resourceUrl =  'api/work-order-schedules/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertLocalDateFromServer(data.createDate);
                        data.expireDate = DateUtils.convertLocalDateFromServer(data.expireDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    copy.expireDate = DateUtils.convertLocalDateToServer(copy.expireDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    copy.expireDate = DateUtils.convertLocalDateToServer(copy.expireDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
