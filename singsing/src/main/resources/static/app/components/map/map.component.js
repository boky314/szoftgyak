'use strict';

// Register `map` component, along with its associated controller and template
angular.
  module('map', ['backEndService']).
  component('map', {
    templateUrl: 'app/components/map/map.template.html',
    controller: ['$scope', '$window', 'BackEndService', 'BackEndModel',
      function MapController($scope, $window, BackEndService, BackEndModel) {
      	$scope.logs = [];
	 	  

        
          var loadAreas = function () {
            BackEndService.getAreas(function (result) {
  
              console.log(result.data);
              
            }, function (error) {
  
              $scope.areas = [];
              console.log(error);
            });
          };
  
          loadAreas();

          $scope.download = function () {
			$window.mermaid.init();
		
        };

	  }
    ]
  });
