'use strict';

describe('Controller Tests', function() {

    describe('AssetSpecificationTypeField Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAssetSpecificationTypeField, MockAssetSpecificationTypeValue, MockAsset;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAssetSpecificationTypeField = jasmine.createSpy('MockAssetSpecificationTypeField');
            MockAssetSpecificationTypeValue = jasmine.createSpy('MockAssetSpecificationTypeValue');
            MockAsset = jasmine.createSpy('MockAsset');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AssetSpecificationTypeField': MockAssetSpecificationTypeField,
                'AssetSpecificationTypeValue': MockAssetSpecificationTypeValue,
                'Asset': MockAsset
            };
            createController = function() {
                $injector.get('$controller')("AssetSpecificationTypeFieldDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'app:assetSpecificationTypeFieldUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
