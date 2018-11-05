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
	 	  
	
      
		$scope.download = function () {
			loadLogs();
		var csv = 'User,Date,Type,Change\n';
		
		
		
    	$scope.logs.forEach(function(row) {
            csv += row.user + ",";
            csv += row.dateTime + ",";
            csv += row.changeType + ",";
            csv += row.change;
            csv += "\n";
   		 });
    	
    	
    	
    	
    	
    	var hiddenElement = document.createElement('a');
    	hiddenElement.href = 'data:text/csv;charset=utf-8,' + encodeURI(csv);
    	hiddenElement.target = '_blank';
    	hiddenElement.download = 'people.csv';
    	hiddenElement.click();
		
		
		
				
        };
		}
    ]
  });
