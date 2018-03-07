(function() {
    'use strict';
    angular
        .module('app')
        .factory('WorkOrderSchedule', WorkOrderSchedule)
        .factory('WorkOrderScheduleByUser', WorkOrderScheduleByUser);

    WorkOrderSchedule.$inject = ['$resource', 'DateUtils'];
    WorkOrderScheduleByUser.$inject = ['$resource'];

    function WorkOrderScheduleByUser ($resource) {
        var resourceUrl =  'api/work-order-schedules-by-user';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
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
                        data.completedDate = DateUtils.convertLocalDateFromServer(data.completedDate);
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
                    copy.completedDate = DateUtils.convertLocalDateToServer(copy.completedDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    copy.expireDate = DateUtils.convertLocalDateToServer(copy.expireDate);
                    copy.completedDate = DateUtils.convertLocalDateToServer(copy.completedDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
