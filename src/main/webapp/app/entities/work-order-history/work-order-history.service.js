(function() {
    'use strict';
    angular
        .module('app')
        .factory('WorkOrderHistoryByAssetId', WorkOrderHistoryByAssetId)
        .factory('WorkOrderHistory', WorkOrderHistory);

    WorkOrderHistory.$inject = ['$resource', 'DateUtils'];
    WorkOrderHistoryByAssetId.$inject = ['$resource', 'DateUtils'];

    function WorkOrderHistoryByAssetId ($resource, DateUtils) {
        var resourceUrl = 'api/work-order-histories/asset/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertDateTimeFromServer(data.createDate);
                    }
                    return data;
                }
            }
        });
    }
    function WorkOrderHistory ($resource, DateUtils) {
        var resourceUrl =  'api/work-order-histories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createDate = DateUtils.convertLocalDateFromServer(data.createDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createDate = DateUtils.convertLocalDateToServer(copy.createDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
