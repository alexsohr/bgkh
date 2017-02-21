(function() {
    'use strict';
    angular
        .module('app')
        .factory('Asset', Asset)
        .factory('AssetImport', AssetImport);

    Asset.$inject = ['$resource'];
    AssetImport.$inject = ['$resource'];

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
            'update': { method:'PUT' },
            'delete': {method: 'DELETE'}
        });
    }

    function AssetImport ($resource) {
        var resourceUrl =  '/api/assets/all';

        return $resource(resourceUrl, {}, {
            'save': {method: 'POST'}
        });
    }



})();
