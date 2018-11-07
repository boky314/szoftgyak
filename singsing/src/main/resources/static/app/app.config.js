'use strict';

angular.
  module('SingSingApp').
  config(['$locationProvider', '$routeProvider',
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
        when('/guards', {
          template: '<guards></guards>'
        }).
        when('/logout', {
          controller: function(){
            window.location.replace('/logout');
          },
          template: '<div></div>'
        }).
        otherwise('/home');

      $locationProvider.html5Mode(true);
    }
  ]).
  constant('appSettings', {
    versionNumber: "1.1.1.0"
  });
