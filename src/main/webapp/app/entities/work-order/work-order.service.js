(function() {
    'use strict';
    angular
        .module('app')
        .factory('WorkOrder', WorkOrder)
        .factory('WorkOrderByAsset', WorkOrderByAsset);

    WorkOrder.$inject = ['$resource', 'DateUtils'];
    WorkOrderByAsset.$inject = ['$resource', 'DateUtils'];

    function WorkOrderByAsset ($resource, DateUtils) {
        var resourceUrl = 'api/work-orders-by-asset/:id';
        return gerCRUD(resourceUrl, $resource, DateUtils);
    }

    function WorkOrder ($resource, DateUtils) {
        var resourceUrl =  'api/work-orders/:id';
        return gerCRUD(resourceUrl, $resource, DateUtils);
    }

    function gerCRUD(resourceUrl, $resource, DateUtils) {
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.trackDate = DateUtils.convertLocalDateFromServer(data.trackDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.trackDate = DateUtils.convertLocalDateToServer(copy.trackDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.trackDate = DateUtils.convertLocalDateToServer(copy.trackDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
