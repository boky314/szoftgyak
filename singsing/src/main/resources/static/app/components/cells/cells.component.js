'use strict';

// Register `cells` component, along with its associated controller and template
angular.
  module('cells', ['backEndService']).
  component('cells', {
    templateUrl: 'app/components/cells/cells.template.html',
    controller: ['$scope', 'BackEndService', 'BackEndModel',
      function CellsController($scope, BackEndService, BackEndModel) {

        $scope.isEditing = false;
        $scope.newCell = {};
        $scope.cells = [];
        $scope.areas = [];
        $scope.prisonersForSelectedCell = [];
        $scope.areaModel = BackEndModel.area;
        $scope.cellModel = BackEndModel.prisoncell;

        var loadCells = function () {
          BackEndService.getCells(function (result) {

            $scope.cells = result.data;
            assigAreasToCells();
            initDatatable();
          }, function (error) {

            $scope.cells = [];
            console.log(error);
          });
        };

        var loadAreas = function () {
          BackEndService.getAreas(function (result) {

            $scope.areas = result.data;
            loadCells();
          }, function (error) {

            $scope.areas = [];
            console.log(error);
          });
        };

        var assigAreasToCells = function () {

          $scope.cells.forEach(cell => {

            var areaIndex = $scope.areas.findIndex(area => area[$scope.areaModel.id] === cell[$scope.cellModel.areaId]);

            if (areaIndex > -1) {

              cell.areaName = $scope.areas[areaIndex][$scope.areaModel.name];
            }
            else {

              cell.areaName = "";
            }
          });
        };

        loadAreas();

        var initDatatable = function () {

          setTimeout(function () {

            var dataTable = $('#cellList_table');
            dataTable.DataTable();
          }, 500);
        };

        var resetNewCell = function () {

          $scope.isEditing = false;
          $scope.newCell = {};
        };

        $scope.submitNewCell = function () {

          if ($scope.cells.findIndex(a => a.id === $scope.newCell.id) > -1) {

            BackEndService.updateCell($scope.newCell,
              function (result) {

                resetNewCell();
                loadCells();
              }, function (error) {

                console.log(error);
              }
            );
          }
          else {

            BackEndService.createCell($scope.newCell,
              function (result) {

                resetNewCell();
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

            $scope.isEditing = true;
            $scope.newCell = $scope.cells[index];
          }
        };

        $scope.deleteCell = function (id) {

          var index = $scope.cells.findIndex(a => a.id === id);

          if (index > -1) {

            var cell = $scope.cells[index];

            BackEndService.deleteCell(cell, function (result) {

              console.log(result);
              loadCells();
            }, function (error) {

              console.log(error);
              loadCells();
            });
          }
        };

        $scope.cancelNewCell = function () {

          resetNewCell();
        };
      }
    ]
  });
