'use strict';

angular.
    module('backEndService', []).
    service('BackEndService', ['$http', 'appSettings',
        function ($http, appSettings) {

            this.getAreas = function (successCallback, errorCallback) {

                $http.get("/area/").then(function (result) {

                    successCallback(result);
                }, function (error) {

                    errorCallback(error);
                });
            };

            this.updateArea = function (area, successCallback, errorCallback) {

                $http.put("/area/update", area).then(
                    function (result) {

                        successCallback(result);
                    }, function (error) {

                        errorCallback(error);
                    }
                );
            };

            this.createArea = function (area, successCallback, errorCallback) {

                $http.post("/area/new", area).then(
                    function (result) {

                        successCallback(result);
                    }, function (error) {

                        errorCallback(error);
                    }
                );
            };

            this.deleteArea = function (area, successCallback, errorCallback) {

                $http.post("/area/delete", area).then(function (result) {

                    successCallback(result);
                }, function (error) {

                    errorCallback(error);
                });
            };

            this.getCells = function (successCallback, errorCallback) {

                $http.get("/prisoncell/").then(function (result) {

                    successCallback(result);
                }, function (error) {

                    errorCallback(error);
                });
            };

            this.updateCell = function (cell, successCallback, errorCallback) {

                $http.put("/prisoncell/update", cell).then(
                    function (result) {

                        successCallback(result);
                    }, function (error) {

                        errorCallback(error);
                    }
                );
            };

            this.createCell = function (cell, successCallback, errorCallback) {

                $http.post("/prisoncell/new", cell).then(
                    function (result) {

                        successCallback(result);
                    }, function (error) {

                        errorCallback(error);
                    }
                );
            };

            this.deleteCell = function (cell, successCallback, errorCallback) {

                $http.post("/prisoncell/delete", cell).then(function (result) {

                    successCallback(result)
                }, function (error) {

                    errorCallback(error);
                });
            };


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
