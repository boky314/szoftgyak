'use strict';

// Register `home` component, along with its associated controller and template
angular.
  module('home', []).
  component('home', {
    templateUrl: 'app/components/home/home.template.html',
    controller: ['$scope', '$location', 'appSettings',
      function HomeController($scope, $location, appSettings) {

        $scope.version = appSettings.versionNumber;
      }
    ]
  });
