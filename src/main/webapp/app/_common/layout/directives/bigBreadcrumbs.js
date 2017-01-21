'use strict';

angular.module('SmartAdmin.Layout').directive('bigBreadcrumbs', function ($rootScope) {
    return {
        restrict: 'EA',
        replace: true,
        template: '<div><h1 class="page-title txt-color-blueDark"></h1></div>',
        scope: {
            items: '=',
            icon: '@'
        },
        link: function (scope, element) {
            var first = _.first(scope.items);
            var icon = scope.icon || $rootScope.getWord('Home');
            element.find('h1').append('<i class="fa-fw fa fa-' + icon + '"></i> ' + $rootScope.getWord(first));
            _.rest(scope.items).forEach(function (item) {
                element.find('h1').append(' <span>> ' + $rootScope.getWord(item) + '</span>')
            })
        }
    }
});
