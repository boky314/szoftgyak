'use strict';

// Register `logs` component, along with its associated controller and template
angular.
  module('logs', ['backEndService']).
  component('logs', {
    templateUrl: 'app/components/logs/logs.template.html',
    controller: ['$scope', 'BackEndService', 'BackEndModel',
      function LogsController($scope, BackEndService, BackEndModel) {
      	$scope.logs = [];
	 	  
  		var loadLogs = function () {
            BackEndService.getLogs(function (result) {

                $scope.logs = result.data;
                initDatatable();
            }, function (error) {

                $scope.logs = [];
                console.log(error);
            });
        };

        loadLogs();
        
        var initDatatable = function () {
         
            setTimeout(function () {

                var dataTable = $('#logList_table');
                dataTable.DataTable();
            }, 500);
            
          };
	 	  
      }
    ]
  });
