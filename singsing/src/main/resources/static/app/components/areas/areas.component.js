'use strict';

// Register `areas` component, along with its associated controller and template
angular.
  module('areas', ['backEndService']).
  component('areas', {
    templateUrl: 'app/components/areas/areas.template.html',
    controller: ['$scope', 'BackEndService', 'BackEndModel',
      function CellsController($scope, BackEndService, BackEndModel) {

        $scope.newArea = {};
        $scope.areas = [];
        $scope.isEditing = false;
        $scope.areaModel = BackEndModel.area;

        var loadAreas = function () {
          BackEndService.getAreas(function (result) {

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
          }, 500);
        };

        var resetNewArea = function () {

          $scope.isEditing = false;
          $scope.newArea = {};
        };

        $scope.submitNewArea = function () {
          if ($scope.areas.findIndex(a => a.id === $scope.newArea.id) > -1) {

            BackEndService.updateArea($scope.newArea,
              function (result) {

                resetNewArea();
                loadAreas();
              }, function (error) {

                console.log(error);
              }
            );
          }
          else {

            BackEndService.createArea($scope.newArea,
              function (result) {

                resetNewArea();
                loadAreas();
              }, function (error) {

                console.log(error);
              }
            );
          }
        };

        $scope.editArea = function (id) {

          var index = $scope.areas.findIndex(a => a.id === id);

          if (index > -1) {

            $scope.isEditing = true;
            $scope.newArea = $scope.areas[index];
          }
        };

        $scope.deleteArea = function (id) {

          var index = $scope.areas.findIndex(a => a.id === id);

          if (index > -1) {

            var area = $scope.areas[index];

            BackEndService.deleteArea(area, function (result) {

              console.log(result);
              loadAreas();
            }, function (error) {

              console.log(error);
              loadAreas();
            });
          }
        };

        $scope.cancelNewArea = function () {

          resetNewArea();
        };
      }
    ]
  });
