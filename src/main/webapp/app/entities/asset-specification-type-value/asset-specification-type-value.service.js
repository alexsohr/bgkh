(function() {
    'use strict';
    angular
        .module('app')
        .factory('AssetSpecificationTypeValue', AssetSpecificationTypeValue);

    AssetSpecificationTypeValue.$inject = ['$resource'];

    function AssetSpecificationTypeValue ($resource) {
        var resourceUrl =  'api/asset-specification-type-values/:id';

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
