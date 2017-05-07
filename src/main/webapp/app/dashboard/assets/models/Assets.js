"use strict";

angular.module('app').factory('Assets', function ($http, APP_CONFIG) {
    return $http.get(APP_CONFIG.apiRootUrl + '/assets.json');
});
