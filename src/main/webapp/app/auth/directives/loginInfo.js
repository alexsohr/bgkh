"use strict";

angular.module('app.auth').directive('loginInfo', function(Users){

    return {
        restrict: 'A',
        templateUrl: 'app/auth/directives/login-info.tpl.html',
        link: function(scope, element, Principal){
            Users.initialized.then(function(){
                scope.user = Users
            });

            scope.isAuthenticated = Principal.isAuthenticated;
        }
    }
})
