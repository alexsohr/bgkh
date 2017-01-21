    'use strict';

    angular
        .module('app')
        .factory('Activate', function ($resource) {
        var service = $resource('api/activate', {}, {
            'get': { method: 'GET', params: {}, isArray: false}
        });

        return service;

});
