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

            this.getPrisoners = function (successCallback, errorCallback) {

                $http.get("/prisoner/").then(function (result) {

                    successCallback(result);
                }, function (error) {

                    errorCallback(error);
                });
            };

            this.updatePrisoner = function (prisoner, successCallback, errorCallback) {

                $http.post("/prisoner/save", prisoner).then(
                    function (result) {

                        successCallback(result);
                    }, function (error) {

                        errorCallback(error);
                    }
                );
            };

            this.createPrisoner = function (prisoner, successCallback, errorCallback) {

                $http.post("/prisoner/new", prisoner).then(
                    function (result) {

                        successCallback(result);
                    }, function (error) {

                        errorCallback(error);
                    }
                );
            };

            this.deletePrisoner = function (prisoner, successCallback, errorCallback) {

                $http.post("/prisoner/delete", prisoner).then(function (result) {

                    successCallback(result);
                }, function (error) {

                    errorCallback(error);
                });
            };

            this.getGuards = function (successCallback, errorCallback) {
                $http.get("/prisonguard/").then(function (result) {

                    successCallback(result);
                }, function (error) {

                    errorCallback(error);
                });
            };
            
            this.getLogs = function (successCallback, errorCallback) {

                $http.get("/auditlog/").then(function (result) {

                    successCallback(result);
                }, function (error) {

                    errorCallback(error);
                });
            };

            this.searchLogs = function (searchStr, successCallback, errorCallback) {
                $http.get("/auditlog/find/" + searchStr).then(function (result) {

                    successCallback(result);
                }, function (error) {

                    errorCallback(error);
                });
            };
      
            this.createGuard = function (guard, successCallback, errorCallback) {

                $http.post("/prisonguard/new", guard).then(
                    function (result) {

                        successCallback(result);
                    }, function (error) {

                        errorCallback(error);
                    }
                );
            };

            this.deleteGuard = function (guard, successCallback, errorCallback) {

                $http.post("/prisonguard/delete", guard).then(function (result) {

                    successCallback(result);
                }, function (error) {

                    errorCallback(error);
                });
            };

            this.getSchedule = function (successCallback, errorCallback) {
                $http.get("/schedule/").then(function (result) {

                    successCallback(result);
                }, function (error) {

                    errorCallback(error);
                });
            };
        }
    ]);
