'use strict';

angular.module('app.auth').factory('Users', function ($http, $q, APP_CONFIG, Principal, ProfileService) {
    var dfd = $q.defer();

    var UserModel = {
        initialized: dfd.promise,
        activated: undefined,
        username: undefined,
        picture: undefined,
        email: undefined,
        firstName: undefined,
        lastName: undefined
    };


    Principal.identity().then(function(account) {
        UserModel.activated = account.activated,
        UserModel.username = account.login;
        UserModel.picture = account.picture;
        UserModel.firstName = account.firstName;
        UserModel.lastName = account.lastName;
        UserModel.email = account.email;
        dfd.resolve(UserModel)
    });

    return UserModel;
});
