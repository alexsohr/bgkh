'use strict';

angular
    .module('app')
    .factory('PasswordResetInit', function ($resource) {
        var service = $resource('api/account/reset_password/init', {}, {});

        return service;
    });
