'use strict';

angular.
  module('backEndService', []).
  service('BackEndService', ['$http', 'appSettings',
    function($http, appSettings) {
    
        this.get = function (path) {

            return $http.get(path);
        };

        this.post = function (path, body) {

            return $http.post(path, body);
        };

        this.put = function (path, body) {

            return $http.put(path, body);
        };

        this.delete = function (path, id, successCallback, errorCallback) {

            return $http.delete(path + "/" + id);
        };
    }
  ]);
