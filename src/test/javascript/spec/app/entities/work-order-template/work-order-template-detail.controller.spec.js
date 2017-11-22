'use strict';

describe('Controller Tests', function() {

    describe('WorkOrderTemplate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWorkOrderTemplate, MockAssetSpecificationType;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWorkOrderTemplate = jasmine.createSpy('MockWorkOrderTemplate');
            MockAssetSpecificationType = jasmine.createSpy('MockAssetSpecificationType');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WorkOrderTemplate': MockWorkOrderTemplate,
                'AssetSpecificationType': MockAssetSpecificationType
            };
            createController = function() {
                $injector.get('$controller')("WorkOrderTemplateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'appApp:workOrderTemplateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
