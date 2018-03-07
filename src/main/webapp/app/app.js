'use strict';

/**
 * @ngdoc overview
 * @name app [smartadminApp]
 * @description
 * # app [smartadminApp]
 *
 * Main module of the application.
 */

angular.module('app', [
    'ngSanitize',
    'ngAnimate',
    'ngStorage',
    'restangular',
    'ui.router',
    'ui.bootstrap',
    'ngFileUpload',
    'angularMoment',
    // Smartadmin Angular Common Module
    'SmartAdmin',

    // App
    'app.home',
    'app.auth',
    'app.admin',
    'app.account',
    'app.layout',
    'app.chat',
    'app.dashboard',
    'app.workOrder',
    'app.calendar',
    'app.tables',
    'app.graphs',
    'app.forms',
    'app.ui',
    'app.widgets',
    'app.appViews',
    'app.smartAdmin'
])
.config(function ($provide, $httpProvider, RestangularProvider) {


    // Intercept http calls.
    $provide.factory('ErrorHttpInterceptor', function ($q) {
        var errorCounter = 0;
        function notifyError(rejection){
            console.log(rejection);
            $.bigBox({
                title: rejection.status + ' ' + rejection.statusText,
                content: rejection.data,
                color: "#C46A69",
                icon: "fa fa-warning shake animated",
                number: ++errorCounter,
                timeout: 6000
            });
        }

        return {
            // On request failure
            requestError: function (rejection) {
                // show notification
                notifyError(rejection);

                // Return the promise rejection.
                return $q.reject(rejection);
            },

            // On response failure
            responseError: function (rejection) {
                // show notification
                notifyError(rejection);
                // Return the promise rejection.
                return $q.reject(rejection);
            }
        };
    });

    // Add the interceptor to the $httpProvider.
    $httpProvider.interceptors.push('ErrorHttpInterceptor');

    RestangularProvider.setBaseUrl(location.pathname.replace(/[^\/]+?$/, ''));

})
.constant('APP_CONFIG', window.appConfig)

.run(function ($rootScope
    , $state, $stateParams, Language, Assets, amMoment, Auth
    ) {

    $rootScope.$state = $state;
    $rootScope.$stateParams = $stateParams;
    $rootScope.lang = {};

    $rootScope.currentLanguage = "ir";
    amMoment.changeLocale('fa');
    Language.getLanguages(function(data){

        $rootScope.currentLanguage = data[0];

        $rootScope.languages = data;

        Language.getLang(data[0].key,function(data){

            $rootScope.lang = data;
        });
    });

    //DEBUG
    $rootScope.$on('$routeChangeStart', function(event, next, current) {
        // next is an object that is the route that we are starting to go to
        // current is an object that is the route where we are currently
        var currentPath = current.originalPath;
        var nextPath = next.originalPath;
        // if (Auth.getPreviousState()) {
        //     var previousState = Auth.getPreviousState();
        //     Auth.resetPreviousState();
        //     $state.go(previousState.name, previousState.params);
        // }
        //
        console.log('Starting to leave %s to go to %s', currentPath, nextPath);
    });
    $rootScope.$on('$locationChangeStart', function(event, newUrl, oldUrl) {
        // both newUrl and oldUrl are strings
        console.log('Starting to leave %s to go to %s', oldUrl, newUrl);
    });
    $rootScope.$on('$routeChangeError', function (evt, current, previous, rejection) {
        console.log('Route error', rejection);
    });
    //END DEBUG

    $rootScope.getWord = function(key){
        if(angular.isDefined($rootScope.lang[key])){
            return $rootScope.lang[key];
        }
        else {
            return key;
        }
    }


    Assets.then(function (response) {
        var rawTreeData = response.data;
        var myTreeData = $rootScope.getTree(rawTreeData, 'demographicId', 'parentId');
        console.log({"tree_data" : myTreeData});
        $rootScope.tree_data = myTreeData;
    });


    $rootScope.getTree = function(data, primaryIdName, parentIdName) {
        if (!data || data.length == 0 || !primaryIdName || !parentIdName)
            return [];

        var tree = [],
            rootIds = [],
            item = data[0],
            primaryKey = item[primaryIdName],
            treeObjs = {},
            parentId,
            parent,
            len = data.length,
            i = 0,
            primeryIds = [];

        while (i < len) {
            item = data[i++];
            primaryKey = item[primaryIdName];
            if (!primeryIds[primaryKey]) {
                treeObjs[primaryKey] = item;
                parentId = item[parentIdName];

                if (parentId) {
                    parent = treeObjs[parentId];

                    if (parent.children) {
                        parent.children.push(item);
                    }
                    else {
                        parent.children = [item];
                    }
                }
                else {
                    rootIds.push(primaryKey);
                }
            }
            primeryIds.push(primaryKey)
        }

        for (var i = 0; i < rootIds.length; i++) {
            tree.push(treeObjs[rootIds[i]]);
        };

        return tree;
    }

    // editableOptions.theme = 'bs3';

});


