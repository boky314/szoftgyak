'use strict';

// Register `prisoners` component, along with its associated controller and template
angular.
module('schedule', ['backEndService']).
component('schedule', {
    templateUrl: 'app/components/schedule/schedule.template.html',
    controller: ['$scope', 'BackEndService', 'BackEndModel',
        function ScheduleController($scope, BackEndService, BackEndModel) {
            $scope.schedule = [];

            var loadSchedule = function () {
                BackEndService.getSchedule(function (result) {
                        $scope.schedule = result.data;
                        console.log(result);
                    },
                    function (error) {
                        $scope.schedule = [];
                        console.log(error);
                    }
                );
            };
            loadSchedule();
        }
    ]
});