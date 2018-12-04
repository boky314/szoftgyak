'use strict';

app.controller('indexController', ['$scope', '$location', 'appSettings',
    'BackEndService',
    function ($scope, $location, appSettings, BackEndService) {

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
        
        var loadDispersion = function () {
            BackEndService.getDispersion(function (result) {

                setDispersion(result.data);
                setTimeout(function () { loadDispersion(); }, 5000);
            }, function (error) {

                setDispersion(0);
                console.log(error);
                setTimeout(function () { loadDispersion(); }, 5000);
            });
        };

        var setFullness = function (fullness) {
            $scope.fullness = fullness;
            $scope.fullnessStyle = {
                "width": $scope.fullness + "%"
            };
        };

		var setDispersion = function(dispersion)  {
			$scope.dispersion = dispersion;
			$scope.dispersionStyle = {
                "width": $scope.dispersion + "%"
            };
		};


        loadFullness();
		loadDispersion();
        $scope.versionNumber = appSettings.versionNumber;
    }]);
