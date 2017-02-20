'use strict';

describe('Controller Tests', function() {

    describe('AssetSpecificationTypeValue Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAssetSpecificationTypeValue, MockAssetSpecificationTypeField;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAssetSpecificationTypeValue = jasmine.createSpy('MockAssetSpecificationTypeValue');
            MockAssetSpecificationTypeField = jasmine.createSpy('MockAssetSpecificationTypeField');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AssetSpecificationTypeValue': MockAssetSpecificationTypeValue,
                'AssetSpecificationTypeField': MockAssetSpecificationTypeField
            };
            createController = function() {
                $injector.get('$controller')("AssetSpecificationTypeValueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'app:assetSpecificationTypeValueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
