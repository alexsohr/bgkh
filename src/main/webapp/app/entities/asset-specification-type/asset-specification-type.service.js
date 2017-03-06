(function() {
    'use strict';
    angular
        .module('app')
        .factory('AssetSpecificationType', AssetSpecificationType);

    AssetSpecificationType.$inject = ['$resource'];

    function AssetSpecificationType ($resource) {
        var resourceUrl =  'api/asset-specification-types/:id';

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
