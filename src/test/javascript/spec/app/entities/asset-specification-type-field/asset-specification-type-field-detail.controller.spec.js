'use strict';

describe('Controller Tests', function() {

    describe('AssetSpecificationTypeField Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAssetSpecificationTypeField, MockAssetSpecificationType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAssetSpecificationTypeField = jasmine.createSpy('MockAssetSpecificationTypeField');
            MockAssetSpecificationType = jasmine.createSpy('MockAssetSpecificationType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AssetSpecificationTypeField': MockAssetSpecificationTypeField,
                'AssetSpecificationType': MockAssetSpecificationType
            };
            createController = function() {
                $injector.get('$controller')("AssetSpecificationTypeFieldDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appApp:assetSpecificationTypeFieldUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
