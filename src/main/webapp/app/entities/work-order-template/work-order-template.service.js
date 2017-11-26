(function() {
    'use strict';
    angular
        .module('app')
        .factory('WorkOrderTemplate', WorkOrderTemplate)
        .factory('WorkOrderTemplateByAssetType', WorkOrderTemplateByAssetType);

    WorkOrderTemplate.$inject = ['$resource'];
    WorkOrderTemplateByAssetType.$inject = ['$resource'];

    function WorkOrderTemplate ($resource) {
        var resourceUrl =  'api/work-order-templates/:id';

        return getCRUD(resourceUrl, $resource);
    }

    function WorkOrderTemplateByAssetType ($resource) {
        var resourceUrl =  'api/work-order-templates-by-asset-type/:id';
        return getCRUD(resourceUrl, $resource);

    }

    function getCRUD (resourceUrl, $resource) {
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
