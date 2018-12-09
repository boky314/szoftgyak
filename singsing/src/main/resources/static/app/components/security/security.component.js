'use strict';

// Register `security` component, along with its associated controller and template
angular.
  module('security', ['backEndService']).
  component('security', {
    templateUrl: 'app/components/security/security.template.html',
    controller: ['$scope', 'BackEndService', 'BackEndModel',
      function CellsController($scope, BackEndService, BackEndModel) {
      	     $scope.security = []; 	  
      	        
      	     var loadAreas = function () {
         	 BackEndService.getSecurity(function (result) {
             	$scope.security = result.data;
             	initDatatable();
          	 }, function (error) {
             	$scope.security = [];
            	console.log(error);
          	 });
        	 };

        	loadAreas();
        	
        	var initDatatable = function () {

          	setTimeout(function () {
            	var dataTable = $('#securityList_table');
            	dataTable.DataTable();
          	}, 500);
          	
        	};
        	
      }
    ]
  });
