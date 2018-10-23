'use strict';

// Register `areas` component, along with its associated controller and template
angular.
  module('areas', ['backEndService']).
  component('areas', {
    templateUrl: 'app/components/areas/areas.template.html',
    controller: ['$scope', '$location', 'appSettings', 'BackEndService',
      function CellsController($scope, $location, appSettings, BackEndService) {

        $scope.newArea = {};
        $scope.areas = [];

        var loadAreas = function () {
          BackEndService.get("/area/").then(function (result) {

            $scope.areas = result.data;
            initDatatable();
          }, function (error) {

            $scope.areas = [];
            console.log(error);
          });
        };

        loadAreas();

        var initDatatable = function () {

          setTimeout(function () {

            var dataTable = $('#areaList_table');
            dataTable.DataTable();
          }, 10);
        };

        $scope.submitNewArea = function()
        {
            if($scope.areas.findIndex(a => a.id === $scope.newArea.id) > -1) {

                BackEndService.put("/area/update", $scope.newArea).then(
                    function (result) {
                        
                      $scope.newArea = {};
                      loadAreas();
                    },function (error) {
    
                        console.log(error);
                    }
                );
            }
            else {

                BackEndService.post("/area/new", $scope.newArea).then(
                    function (result) {
                        
                      $scope.newArea = {};
                      loadAreas();
                    },function (error) {
    
                        console.log(error);
                    }
                );
            }
        };

        $scope.editArea = function (id) {

            var index = $scope.areas.findIndex(a => a.id === id);

            if(index > -1) {

                $scope.newArea = $scope.areas[index];
            }
        };

        $scope.deleteArea = function (id) {
            
            var index = $scope.areas.findIndex(a => a.id === id);

            if(index > -1) {

                var area = $scope.areas[index];

                BackEndService.post("/area/delete", area).then(function (result) {

                    console.log(result);
                  }, function (error) {
        
                    console.log(error);
                  }).then(function () {
        
                    loadAreas();
                  });
            }
        };

        $scope.cancelNewArea = function () {

            $scope.newArea = {};
        };
      }
    ]
  });
