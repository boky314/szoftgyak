'use strict';

// Register `prisoners` component, along with its associated controller and template
angular.
  module('prisoners', ['backEndService']).
  component('prisoners', {
    templateUrl: 'app/components/prisoners/prisoners.template.html',
    controller: ['$scope', 'BackEndService', 'BackEndModel',
      function PrisonersController($scope, BackEndService, BackEndModel) {

        $scope.isEditing = false;
        $scope.newPrisoner = {};
        $scope.prisoners = [];
        $scope.cells = [];
        $scope.prisonerModel = BackEndModel.prisoner;
        $scope.cellModel = BackEndModel.prisoncell;

        var loadCells = function () {
          BackEndService.getCells(function (result) {

            $scope.cells = result.data;
          }, function (error) {

            $scope.cells = [];
            console.log(error);
          });
        };

        var loadPrisoners = function () {
          BackEndService.getPrisoners(function (result) {

            $scope.prisoners = result.data;
            initDatatable();
          }, function (error) {

            $scope.prisoners = [];
            console.log(error);
            initDatatable();
          });
        };

        loadPrisoners();
        loadCells();

        var initDatatable = function () {

          setTimeout(function () {

            var dataTable = $('#prisonerList_table');
            dataTable.DataTable();
          }, 500);
        };

        var resetNewPrisoner = function () {

          $scope.isEditing = false;
          $scope.newPrisoner = {};
        };

        $scope.submitNewPrisoner = function () {

          if ($scope.prisoners.findIndex(a => a[$scope.prisonerModel.id] === $scope.newPrisoner[$scope.prisonerModel.id]) > -1) {

            BackEndService.updatePrisoner($scope.newPrisoner,
              function (result) {

                resetNewPrisoner();
                loadPrisoners();
              }, function (error) {

                console.log(error);
              }
            );
          }
          else {

            BackEndService.createPrisoner($scope.newPrisoner,
              function (result) {

                resetNewPrisoner();
                loadPrisoners();
              }, function (error) {

                console.log(error);
              }
            );
          }
        };

        $scope.editPrisoner = function (id) {

          var index = $scope.prisoners.findIndex(a => a[$scope.prisonerModel.id] === id);

          if (index > -1) {

            $scope.isEditing = true;
            $scope.newPrisoner = $scope.prisoners[index];
          }
        };

        $scope.deletePrisoner = function (id) {

          var index = $scope.prisoners.findIndex(a => a[$scope.prisonerModel.id] === id);

          if (index > -1) {

            var prisoner = $scope.prisoners[index];

            BackEndService.deletePrisoner(prisoner, function (result) {

              console.log(result);
              loadPrisoners();
            }, function (error) {

              console.log(error);
              loadPrisoners();
            });
          }
        };

        $scope.cancelNewPrisoner = function () {

          resetNewPrisoner();
        };
      }
    ]
  });
