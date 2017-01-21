(function() {
    'use strict';
    angular
        .module('app')
        .factory('WorkOrder', WorkOrder);

    WorkOrder.$inject = ['$resource', 'DateUtils'];

    function WorkOrder ($resource, DateUtils) {
        var resourceUrl =  'api/work-orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateCompleted = DateUtils.convertLocalDateFromServer(data.dateCompleted);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateCompleted = DateUtils.convertLocalDateToServer(copy.dateCompleted);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateCompleted = DateUtils.convertLocalDateToServer(copy.dateCompleted);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
