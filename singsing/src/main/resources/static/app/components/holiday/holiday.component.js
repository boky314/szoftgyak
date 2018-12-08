'use strict';

// Register `holiday` component, along with its associated controller and
// template
angular.
    module('holiday', ['backEndService']).
    component('holiday', {
        templateUrl: 'app/components/holiday/holiday.template.html',
        controller: ['$scope', 'BackEndService', 'BackEndModel',
            function HolidaysController($scope, BackEndService, BackEndModel) {

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
7
                $scope.submitNewHoliday = function () {
                	$scope.newHoliday.status = 'NEW';
                    BackEndService.createHoliday($scope.newHoliday,
                        function (result) {

                            $scope.newHoliday = {};
                            loadHolidays();
                        }, function (error) {
                            console.log(error);
                        }
                    );
                };

                $scope.approveHoliday = function (id, status) {

                    var index = $scope.holidays.findIndex(a => a[$scope.holidayModel.id] === id);

                    if (index > -1) {

                        var holiday = $scope.holidays[index];
                        
                        holiday.status = status;
                        
                        BackEndService.updateHoliday(holiday, function (result) {

                            console.log(result);
                            loadHolidays();
                        }, function (error) {

                            console.log(error);
                            loadHolidays();
                        });
                    }
                };

            
            }
        ]
    });
