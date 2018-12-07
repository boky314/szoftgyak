'use strict';

app.controller('indexController', ['$scope', '$location', 'appSettings',
    'BackEndService',
    function ($scope, $location, appSettings, BackEndService) {
		$scope.userPrivileges = 'GUARD';
	
        var loadFullness = function () {
            BackEndService.getFullness(function (result) {

                setFullness(result.data * 100);
                setTimeout(function () { loadFullness(); }, 5000);
            }, function (error) {

                setFullness(0);
                console.log(error);
                setTimeout(function () { loadFullness(); }, 5000);
            });
        };

        var setFullness = function (fullness) {
            $scope.fullness = fullness;
            $scope.fullnessStyle = {
                "width": $scope.fullness + "%"
            };
        };
        
        var userPrivileges = function (){
        	
        	BackEndService.getUserPrivileges(function(result){
         		
        		var response = result.data;
        		$scope.userPrivileges = response.result; 

        	}, function (error) {
                    console.log(error);
        	});
        };
        
        userPrivileges();

        loadFullness();

        $scope.versionNumber = appSettings.versionNumber;
    }
]
);
