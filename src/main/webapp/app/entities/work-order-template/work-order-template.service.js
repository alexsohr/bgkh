(function() {
    'use strict';
    angular
        .module('app')
        .factory('WorkOrderTemplate', WorkOrderTemplate);

    WorkOrderTemplate.$inject = ['$resource'];

    function WorkOrderTemplate ($resource) {
        var resourceUrl =  'api/work-order-templates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
