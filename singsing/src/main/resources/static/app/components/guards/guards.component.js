'use strict';

// Register `guards` component, along with its associated controller and template
angular.
    module('guards', ['backEndService']).
    component('guards', {
        templateUrl: 'app/components/guards/guards.template.html',
        controller: ['$scope', 'BackEndService', 'BackEndModel',
            function CellsController($scope, BackEndService, BackEndModel) {

                $scope.newGuard = {};
                $scope.guards = [];
                $scope.guardModel = BackEndModel.prisonGuard;

                var loadGuards = function () {
                    BackEndService.getGuards(function (result) {

                        $scope.guards = result.data;
                        initDatatable();
                    }, function (error) {

                        $scope.guards = [];
                        console.log(error);
                    });
                };

                loadGuards();

                var initDatatable = function () {

                    setTimeout(function () {

                        var dataTable = $('#guardList_table');
                        dataTable.DataTable();
                    }, 500);
                };

                $scope.submitNewGuard = function () {

                    BackEndService.createGuard($scope.newGuard,
                        function (result) {

                            $scope.newGuard = {};
                            loadGuards();
                        }, function (error) {

                            console.log(error);
                        }
                    );
                };

                $scope.deleteGuard = function (name) {

                    var index = $scope.guards.findIndex(a => a[$scope.guardModel.name] === name);

                    if (index > -1) {

                        var guard = $scope.guards[index];

                        BackEndService.deleteGuard(guard, function (result) {

                            console.log(result);
                            loadGuards();
                        }, function (error) {

                            console.log(error);
                            loadGuards();
                        });
                    }
                };

                $scope.cancelNewGuard = function () {

                    $scope.newGuard = {};
                };
            }
        ]
    });
