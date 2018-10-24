'use strict';

// Register `cells` component, along with its associated controller and template
angular.
  module('cells', ['backEndService']).
  component('cells', {
    templateUrl: 'app/components/cells/cells.template.html',
    controller: ['$scope', '$location', 'appSettings', 'BackEndService',
      function CellsController($scope, $location, appSettings, BackEndService) {

        $scope.newCell = {};
        $scope.cells = [];
        $scope.areas = [];
        $scope.prisonersForSelectedCell = [];

        var loadCells = function () {
          BackEndService.get("/prisoncell/").then(function (result) {

            $scope.cells = result.data;
            initDatatable();
          }, function (error) {

            $scope.cells = [];
            console.log(error);
          });
        };

        var loadAreas = function () {
          BackEndService.get("/area/").then(function (result) {

            $scope.areas = result.data;
            initDatatable();
          }, function (error) {

            $scope.areas = [];
            console.log(error);
          });
        };

        loadCells();
        loadAreas();

        var initDatatable = function () {

          setTimeout(function () {

            var dataTable = $('#cellList_table');
            dataTable.DataTable();
          }, 10);
        };

        $scope.submitNewCell = function () {

          if ($scope.cells.findIndex(a => a.id === $scope.newCell.id) > -1) {

            BackEndService.put("/prisoncell/update", $scope.newCell).then(
              function (result) {

                $scope.newCell = {};
                loadCells();
              }, function (error) {

                console.log(error);
              }
            );
          }
          else {

            BackEndService.post("/prisoncell/new", $scope.newCell).then(
              function (result) {

                $scope.newCell = {};
                loadCells();
              }, function (error) {

                console.log(error);
              }
            );
          }
        };

        $scope.editCell = function (id) {

          var index = $scope.cells.findIndex(a => a.id === id);

          if (index > -1) {

            $scope.newCell = $scope.cells[index];
          }
        };

        $scope.deleteCell = function (id) {

          var index = $scope.cells.findIndex(a => a.id === id);

          if (index > -1) {

            var cell = $scope.cells[index];

            BackEndService.post("/prisoncell/delete", cell).then(function (result) {

              console.log(result);
            }, function (error) {

              console.log(error);
            }).then(function () {

              loadCells();
            });
          }
        };

        $scope.cancelNewCell = function () {

          $scope.newCell = {};
        };
      }
    ]
  });
