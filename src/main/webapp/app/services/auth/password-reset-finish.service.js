
    'use strict';

    angular
        .module('app')
        .factory('PasswordResetFinish', function ($resource) {
        var service = $resource('api/account/reset_password/finish', {}, {});

        return service;

});
