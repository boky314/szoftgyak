'use strict';

// Register `prisoners` component, along with its associated controller and
// template
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
        $scope.areas = [];
        $scope.areaModel = BackEndModel.area;
        $scope.prisonerModel = BackEndModel.prisoner;
        $scope.cellModel = BackEndModel.prisoncell;
        $scope.cellSecurityModel = {};
        $scope.newPrisoner.prisonerSecurity = 'Regular';
        
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
        
        var loadAreas = function () {
            BackEndService.getAreas(function (result) {

              $scope.areas = result.data;
              loadCells();
            }, function (error) {

              $scope.areas = [];
              console.log(error);
            });
          };
          
          
        loadAreas();
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
          $scope.newPrisoner.prisonerSecurity = 'Regular';
        };

        $scope.security = {
        		availableSecurity: [
        	      {name: 'Regular'},
        	      {name: 'Dangerous'},
        	      {name: 'Violent'}
        	    ],
        	    selectedSecurity: {name: 'Regular'}
       };
        
        $scope.addSecurityLevel = function (name) {
          	$scope.newPrisoner.prisonerSecurity = name;
        	
          }.bind($scope);
          
        var validatePrisoner =  function (){
        	var areaSecurityLevel;
        	var areaId;
        	
            var indexCell = $scope.cells.findIndex(a => a[$scope.cellModel.cellId] === $scope.newPrisoner.cellId);

            if (indexCell > -1) {
              areaId = $scope.cells[indexCell].areaId;
            }
            
            
            var indexArea = $scope.areas.findIndex(a => a[$scope.areaModel.areaId] === areaId);
            if (indexArea > -1) {
              areaSecurityLevel = $scope.cells[indexArea].areaSecurity;
            }

            
        	$scope.cellSecurityModel = $scope.cells[$scope.newPrisoner.cellId - 1];
        	$scope.areaModel = $scope.areas[$scope.cellSecurityModel.areaId - 1];
          	var areaSecurityLevel = $scope.areaModel.areaSecurity;        
        	
          	var prisonerLevelValue = $scope.newPrisoner.prisonerSecurity;
               
          	
            console.log('Area security '+ $scope.areaModel.areaSecurity);
          	
          	var messageText = $('#requiredSecurity');
          	var cellErrorBody = $('#cellError');
          	
          	 if(prisonerLevelValue == 'Regular'){
          		 console.log('It ok, regular');
          		 cellErrorBody.hide();
                 return true;
             }else if(prisonerLevelValue == 'Dangerous' && areaSecurityLevel == 'Medium'){
            	 console.log('Its not ok');
            	 $scope.requiredSecurity = 'Required Area security level is= High or Priority';
           	  	 cellErrorBody.show();
                 return false;
             }
             else if(prisonerLevelValue == 'Violent' && areaSecurityLevel != 'Priority'){
            	 console.log('Its not ok, Violent!!');
            	 $scope.requiredSecurity = 'Required Area security level is = Priority';
           	     cellErrorBody.show();
                 return false;
             }
             else{
           	  cellErrorBody.hide();
                 return true;
             }
           };
        
        $scope.submitNewPrisoner = function () {
        	
         var result = validatePrisoner();

         if(result){
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
        }
        else {
          loadPrisoners();
        }
      }


        $scope.editPrisoner = function (id) {

          var index = $scope.prisoners.findIndex(a => a[$scope.prisonerModel.id] === id);

          if (index > -1) {

            $scope.isEditing = true;
            $scope.newPrisoner = $scope.prisoners[index];
            $scope.security.selectedSecurity.name = $scope.newPrisoner.prisonerSecurity;
            $scope.newPrisoner.releaseDate = new Date($scope.prisoners[index].releaseDate);
            
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