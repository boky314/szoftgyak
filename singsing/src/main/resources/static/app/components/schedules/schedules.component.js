'use strict';

// Register `prisoners` component, along with its associated controller and template
angular.
module('schedules', ['backEndService']).
component('schedules', {
    templateUrl: 'app/components/schedules/schedules.template.html',
    controller: ['$scope', 'BackEndService', 'BackEndModel',
        function ScheduleController($scope, BackEndService, BackEndModel) {
            $scope.schedule = [];
            $scope.totalExtraWork = 0;

            var calculateTotalExtraWork = function () {
              for(var key in $scope.schedule)
              {
                  var  extra =  $scope.schedule[key].extraWorkPerWeek;
                  for( var i = 0; i < 4; ++i )
                  {
                      $scope.totalExtraWork+= extra[i];
                  }
              }
            };

            var loadSchedule = function () {
                BackEndService.getSchedule(function (result) {
                        $scope.schedule = result.data;
                        calculateTotalExtraWork();
                        //console.log(result);
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