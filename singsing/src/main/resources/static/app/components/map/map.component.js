'use strict';

// Register `map` component, along with its associated controller and template
angular.
  module('map', ['backEndService']).
  component('map', {
    templateUrl: 'app/components/map/map.template.html',
    controller: ['$scope', 'BackEndService', 'BackEndModel',
      function MapController($scope, BackEndService, BackEndModel) {
      	$scope.logs = [];
	 	  
  		var loadLogs = function () {
            BackEndService.getLogs(function (result) {

                $scope.logs = result.data;
                //initDatatable();
            }, function (error) {

                $scope.logs = [];
                console.log(error);
            });
        };


        loadLogs();
        


	  }
    ]
  });
