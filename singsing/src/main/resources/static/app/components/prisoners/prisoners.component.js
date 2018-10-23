'use strict';

// Register `prisoners` component, along with its associated controller and template
angular.
  module('prisoners', ['backEndService']).
  component('prisoners', {
    templateUrl: 'app/components/prisoners/prisoners.template.html',
    controller: ['$scope', '$location', 'BackEndService',
      function PrisonersController($scope, $location, BackEndService) {

        $scope.newPrisoner = {};
        $scope.prisoners = [];
        $scope.cells = [];

        var loadCells = function () {
          BackEndService.get("/prisoncell/").then(function (result) {

            $scope.cells = result.data;
            initDatatable();
          }, function (error) {

            $scope.cells = [];
            console.log(error);
          });
        };

        var loadPrisoners = function () {
          BackEndService.get("/prisoner/").then(function (result) {

            $scope.prisoners = result.data;
          }, function (error) {

            $scope.prisoners = [];
            console.log(error);
          }).then(function () {

            initDatatable();
          });
        };

        loadPrisoners();
        loadCells();

        var initDatatable = function () {

          setTimeout(function () {

            var dataTable = $('#prisonerList_table');
            dataTable.DataTable();
          }, 10);
        };

        $scope.submitNewPrisoner = function () {

          if ($scope.prisoners.findIndex(a => a.id === $scope.newPrisoner.id) > -1) {

            BackEndService.post("/prisoner/save", $scope.newPrisoner).then(
              function (result) {

                $scope.newPrisoner = {};
                loadPrisoners();
              }, function (error) {

                console.log(error);
              }
            );
          }
          else {

            BackEndService.post("/prisoner/new", $scope.newPrisoner).then(
              function (result) {
  
                $scope.newPrisoner = {};
                loadPrisoners();
              }, function (error) {
  
                console.log(error);
              }
            );
          }
        };

        $scope.editPrisoner = function (id) {

          var index = $scope.prisoners.findIndex(a => a.id === id);

          if (index > -1) {

            $scope.newPrisoner = $scope.prisoners[index];
          }
        };

        $scope.deletePrisoner = function (id) {

          var index = $scope.prisoners.findIndex(a => a.id === id);

          if (index > -1) {

            var prisoner = $scope.prisoners[index];

            BackEndService.post("/prisoner/delete", prisoner).then(function (result) {

              console.log(result);
            }, function (error) {
  
              console.log(error);
            }).then(function () {
  
              loadPrisoners();
            });
          }
        };

        $scope.cancelNewPrisoner = function () {

          $scope.newPrisoner = {};
        };
      }
    ]
  });
