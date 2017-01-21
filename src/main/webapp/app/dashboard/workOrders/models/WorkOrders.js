"use strict";

angular.module('app').factory('WorkOrders', function ($http, APP_CONFIG) {
    return $http.get(APP_CONFIG.apiRootUrl + '/work-orders.json');
});