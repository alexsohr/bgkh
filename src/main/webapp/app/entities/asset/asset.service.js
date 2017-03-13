(function() {
    'use strict';
    angular
        .module('app')
        .factory('Asset', Asset)
        .factory('AssetImport', AssetImport)
        .factory('AssetCopy', AssetCopy)
        .factory('AssetManufacture', AssetManufacture)
        .factory('AssetNames', AssetNames)
        .factory('AssetLocations', AssetLocations);

    Asset.$inject = ['$resource'];
    AssetImport.$inject = ['$resource'];
    AssetCopy.$inject = ['$resource'];
    AssetManufacture.$inject = ['$resource'];
    AssetLocations.$inject = ['$resource'];
    AssetNames.$inject = ['$resource'];

    function AssetManufacture($resource) {
        var resourceUrl = 'api/assets/manufactures';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }

    function AssetNames($resource) {
        var resourceUrl = 'api/assets/names';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
    function AssetLocations($resource) {
        var resourceUrl = 'api/assets/locations';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
    function Asset ($resource) {
        var resourceUrl =  'api/assets/:id';

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
            'save': {method: 'POST'},
            'update': { method:'PUT'},
            'delete': {method: 'DELETE'}
        });
    }

    function AssetImport ($resource) {
        var resourceUrl =  '/api/assets/all';

        return $resource(resourceUrl, {}, {
            'save': {method: 'POST'}
        });
    }

    function AssetCopy($resource) {
        var resourceUrl = '/api/assets/copy';
        return $resource(resourceUrl, {}, {
            'save': {method: 'POST'}
        });
    }
})();
