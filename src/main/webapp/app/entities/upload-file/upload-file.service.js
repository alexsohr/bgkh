(function() {
    'use strict';
    angular
        .module('appApp')
        .factory('UploadFile', UploadFile);

    UploadFile.$inject = ['$resource'];

    function UploadFile ($resource) {
        var resourceUrl =  'api/upload-files/:id';

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
