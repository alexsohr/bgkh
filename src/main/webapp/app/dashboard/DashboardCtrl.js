'use strict';

angular.module('app.dashboard').controller('DashboardCtrl', function ($rootScope, $scope, notifications) {

    $scope.notifications = notifications.data;

    // Stats Display With Flot Chart

    var workOrders = [
        [1, 27],
        [2, 34],
        [3, 51],
        [4, 48],
        [5, 55],
        [6, 65],
        [7, 61],
        [8, 70],
        [9, 65],
        [10, 75],
        [11, 57],
        [12, 59],
        [13, 62]
    ];
    var designatedTime = [
        [1, 25],
        [2, 31],
        [3, 45],
        [4, 37],
        [5, 38],
        [6, 40],
        [7, 47],
        [8, 55],
        [9, 43],
        [10, 50],
        [11, 47],
        [12, 39],
        [13, 47]
    ];
    var workOrder = {
        label: $rootScope.getWord('Designated time'),
        data: workOrders,
        color: "rgb(255,0,0)",
        lines: {
            show: true,
            lineWidth: 2,
            fill: true,
            fillColor: {
                colors: [
                    {
                        opacity: 0.1
                    },
                    {
                        opacity: 0.13
                    }
                ]
            }
        },
        points: {
            show: true
        }
    };
    var designated = {
        label: $rootScope.getWord('Work time'),
        data: designatedTime,
        color: "rgb(0,0,255)",
        lines: {
            show: true,
            lineWidth: 2,
            fill: true,
            fillColor: {
                colors: [
                    {
                        opacity: 0.1
                    },
                    {
                        opacity: 0.13
                    }
                ]
            }
        },
        points: {
            show: true
        }
    };

    $scope.statsData = [workOrder, designated];
    $scope.statsDisplayOptions = {
        grid: {
            hoverable: true
        },
        // colors: ["#568A89", "#3276B1"],
        tooltip: true,
        tooltipOpts: {
            // content : "Value <b>%x</b> Value <span>%y</span>",
            content: "<span>%y</span> ساعت",
            defaultTheme: false
        },
        xaxis: {
            ticks: [
                [1, "1/4"],
                [2, "1/5"],
                [3, "1/6"],
                [4, "1/7"],
                [5, "1/8"],
                [6, "1/9"],
                [7, "1/10"],
                [8, "1/11"],
                [9, "1/12"],
                [10, "1/13"],
                [11, "1/14"],
                [12, "1/15"],
                [13, "1/16"]
            ]
        },
        yaxes: {
            tickFormatter: function (val, axis) {
                return val + " h";
            }
        }
    };
    $scope.workOrderShow = true;
    $scope.designatedTimeShow = true;
    $scope.$watch('workOrderShow', function (toggle) {
        reveniewWorkOrderToggle(workOrder, toggle);
    });
    $scope.$watch('designatedTimeShow', function (toggle) {
        reveniewWorkOrderToggle(designated, toggle);
    });

    function reveniewWorkOrderToggle(element, toggle) {
        if (toggle) {
            if ($scope.statsData.indexOf(element) == -1)
                $scope.statsData.push(element)
        } else {
            $scope.statsData = _.without($scope.statsData, element);
        }
    }

    /* Live stats TAB 3: Revenew  */

    var water = [
        [1, 2500],
        [2, 3200],
        [3, 4500],
        [4, 3700],
        [5, 3800],
        [6, 4000],
        [7, 4700],
        [8, 5500],
        [9, 4300],
        [10, 5000],
        [11, 4700],
        [12, 3900],
        [13, 4700]
    ];

    var electricity = [
        [1, 2700],
        [2, 3400],
        [3, 5100],
        [4, 4800],
        [5, 5500],
        [6, 6500],
        [7, 6100],
        [8, 7000],
        [9, 6500],
        [10, 7500],
        [11, 5700],
        [12, 5900],
        [13, 6200]
    ];

    var waterBills = {
        label: $rootScope.getWord("Water bill"),
        data: water,
        bars: {
            show: true,
            align: "center",
            barWidth: 0.5
        }
    };
    $scope.waterShow = true;

    $scope.$watch('waterShow', function (toggle) {
        reveniewElementToggle(waterBills, toggle);
    });


    var electricityBills = {
        label: $rootScope.getWord("Electricity bill"),
        data: electricity,
        color: '#3276B1',
        lines: {
            show: true,
            lineWidth: 3
        },
        points: {
            show: true
        }
    };

    $scope.electricityShow = true;

    $scope.$watch('electricityShow', function (toggle) {
        reveniewElementToggle(electricityBills, toggle);
    });

    $scope.revenewData = [waterBills, electricityBills];

    function reveniewElementToggle(element, toggle) {
        if (toggle) {
            if ($scope.revenewData.indexOf(element) == -1)
                $scope.revenewData.push(element)
        } else {
            $scope.revenewData = _.without($scope.revenewData, element);
        }
    }

    $scope.revenewDisplayOptions = {
        grid: {
            hoverable: true
        },
        tooltip: true,
        tooltipOpts: {
            content: '%y',
            // dateFormat: '%b %y',
            defaultTheme: false
        },
        xaxis: {
            ticks: [
                [1, "1395/1"],
                [2, "1395/2"],
                [3, "1395/3"],
                [4, "1395/4"],
                [5, "1395/5"],
                [6, "1395/6"],
                [7, "1395/7"],
                [8, "1395/8"],
                [9, "1395/9"],
                [10, "1395/10"],
                [11, "1395/11"],
                [12, "1395/12"],
                [13, "1396/1"]
            ]
        },
        yaxes: {}
    };

});