'use strict';

app.controller('indexController', ['$scope', '$location', 'appSettings',
    function ($scope, $location, appSettings) {

    $scope.versionNumber = appSettings.versionNumber;
}]);
