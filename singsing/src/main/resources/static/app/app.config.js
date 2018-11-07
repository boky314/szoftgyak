'use strict';

angular.
  module('SingSingApp').
  config(['$locationProvider' ,'$routeProvider',
    function config($locationProvider, $routeProvider) {

      $routeProvider.
        when('/home', {
          template: '<home></home>'
        }).
        when('/cells', {
          template: '<cells></cells>'
        }).
        when('/prisoners', {
          template: '<prisoners></prisoners>'
        }).
        when('/areas', {
          template: '<areas></areas>'
        }).
        when('/schedule',{
           template: '<schedule></schedule>'
        }).
        otherwise('/home');

        $locationProvider.html5Mode(true);
    }
  ]).
  constant('appSettings', {
    versionNumber: "1.1.0.0"
  });
