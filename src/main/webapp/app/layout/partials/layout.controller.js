(function() {
    'use strict';

    angular
        .module('app')
        .controller('LayoutController', LayoutController);

    LayoutController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', 'Users'];

    function LayoutController ($state, Auth, Principal, ProfileService, LoginService, Users) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.user = {};
        Users.initialized.then(function(){
            vm.user = Users
        });
        console.log(Users.lastName);
        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
