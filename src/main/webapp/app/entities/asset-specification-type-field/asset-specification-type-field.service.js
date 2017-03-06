(function() {
    'use strict';
    angular
        .module('app')
        .factory('AssetSpecificationTypeField', AssetSpecificationTypeField)
        .factory('AssetSpecificationTypeFieldByType', AssetSpecificationTypeFieldByType);

    AssetSpecificationTypeField.$inject = ['$resource'];
    AssetSpecificationTypeFieldByType.$inject = ['$resource'];

    function AssetSpecificationTypeFieldByType ($resource) {
        var resourceUrl =  'api/asset-specification-type-fields/byType/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
    function AssetSpecificationTypeField ($resource) {
        var resourceUrl =  'api/asset-specification-type-fields/:id';

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
