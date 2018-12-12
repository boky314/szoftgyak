'use strict';

// Register `holiday` component, along with its associated controller and
// template
angular.
    module('holiday', ['backEndService']).
    component('holiday', {
        templateUrl: 'app/components/holiday/holiday.template.html',
        controller: ['$scope', 'BackEndService', 'BackEndModel',
            function HolidaysController($scope, BackEndService, BackEndModel) {

        		$scope.today = new Date();
        		$scope.isGuard = false;
                $scope.newHoliday = {};
                $scope.holidays = [];
                $scope.holidayModel = BackEndModel.holiday;

                             
                var userIsGuardCheck = function (){
                	
                	BackEndService.getUserPrivileges(function(result){
                 		
                		var userGroup = result.data;
                		console.log(userGroup.result);
                		if(userGroup.result == 'GUARD'){
                			$scope.isGuard = true;
                		}else{
                			$scope.isGuard = false;
                		}
                		
                	}, function (error) {
                		$scope.isGuard = false;
                            console.log(error);
                	});
                };
                
                userIsGuardCheck();
                
                var loadHolidays = function () {
                	
                    BackEndService.getHolidays(function (result) {

                        $scope.holidays = result.data;
                        initDatatable();
                    }, function (error) {

                        $scope.holidays = [];
                        console.log(error);
                    });
                };

                loadHolidays();
    
                
                var initDatatable = function () {

                    setTimeout(function () {

                        var dataTable = $('#holidayList_table');
                        dataTable.DataTable();
                    }, 500);
                };

                $scope.submitNewHoliday = function () {
                	
                	var result = validateHoliday();
                	
                	if(result){
                		$scope.newHoliday.status = 'NEW';
                		BackEndService.createHoliday($scope.newHoliday,
                				function (result) {

                            		$scope.newHoliday = {};
                            		loadHolidays();
                        		}, function (error) {
                        			console.log(error);
                        		}
                		);
                	}
                };
                
               $scope.deleteHoliday = function(id){
            	   var index = $scope.holidays.findIndex(a => a[$scope.holidayModel.id] === id);

                   if (index > -1) {

                     var holiday = $scope.holidays[index];

                     BackEndService.deleteHoliday(holiday, function (result) {

                       console.log(result);
                       loadHolidays();
                     }, function (error) {

                       console.log(error);
                       loadHolidays();
                     });
                   }  
               }; 
               
                $scope.approveHoliday = function (id, status) {

                    var index = $scope.holidays.findIndex(a => a[$scope.holidayModel.id] === id);

                    if (index > -1) {

                        var holiday = $scope.holidays[index];
                        
                        holiday.status = status;
                        
                        BackEndService.updateHoliday(holiday, function (result) {
                            loadHolidays();
                        }, function (error) {

                            console.log(error);
                            loadHolidays();
                        });
                    }
                };
            
                $scope.cancelNewHoliday = function () {
                    resetNewHoliday();
                };
                  
                  var resetNewHoliday = function () {
                	  $scope.newHoliday = {};                      
                };
                
                var validateHoliday =  function (){
                	var fromDate = $scope.newHoliday.fromDate;
                	var toDate = $scope.newHoliday.toDate;
                	
                  	var holidayErrorBody = $('#holidayError');
                  	var holidayTodayErrorBody = $('#holidayTodayError');
                  	
                  	var myToday = new Date($scope.today.getFullYear(), $scope.today.getMonth(), $scope.today.getDate(), 0, 0, 0);
                  	
                  	console.log(fromDate , myToday);
                  	
                  	if(fromDate < myToday){
                  		holidayTodayErrorBody.show();
                  		return false;
                  	}else if(fromDate < toDate){
                  		holidayTodayErrorBody.hide();
                  		holidayErrorBody.hide();
                         return true;
                     }else if(toDate < fromDate){
                    	 holidayTodayErrorBody.hide();
                    	 holidayErrorBody.show();
                         return false;
                     }else{
                    	 holidayTodayErrorBody.hide();
                    	 holidayErrorBody.hide();
                         return true;
                     }
                   };
                
            }
        ]
    });
