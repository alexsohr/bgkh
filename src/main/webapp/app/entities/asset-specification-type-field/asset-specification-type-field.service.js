(function() {
    'use strict';
    angular
        .module('app')
        .factory('AssetSpecificationTypeField', AssetSpecificationTypeField);

    AssetSpecificationTypeField.$inject = ['$resource'];

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
