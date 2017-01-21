'use strict';

angular
    .module('app')
    .factory('Password', function ($resource) {
        var service = $resource('api/account/change_password', {}, {});

        return service;

    });
