'use strict';

// Define the `SingSingApp` module
var app = angular.module('SingSingApp', [
  'ngRoute',
  'backEndService',
  'home',
  'cells',
  'prisoners',
  'areas',
  'guards',
  'backEndModel'
]);
