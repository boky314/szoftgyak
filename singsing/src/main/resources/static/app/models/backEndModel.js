'use strict';

angular.
    module('backEndModel', []).
    factory('BackEndModel', [
        function () {

            const backEndModel = {

                area: {
                    id: "id",
                    name: "name",
                    areaSecurity : "areaSecurity",
                    guardNumber: "guardNumber"
                },

                prisoncell: {
                    id: "id",
                    space: "space",
                    cellDescription: "cellDesc",
                    floor: "floor",
                    prisoners: "prisoners",
                    areaId: "areaId",
                    prisonCellSecurity: "prisonCellSecurity"
                },

                prisoner: {
                    id: "id",
                    prisonerName: "prisonerName",
                    incident: "incident",
                    releaseDate: "releaseDate",
                    placeDate: "placeDate",
                    cellId: "cellId",
                    prisonerSecurity: "prisonerSecurity",
                    guardNumber: "guardNumber"
                },

                prisonGuard: {
                    id: "id",
                    name: "prisonGuardName",
                    workPerDay: "workPerDay",
                    workPerWeek: "workPerWeek"
                },
                holiday: {
                	id: "id",
                    guardName: "guardName",
                    fromDate: "fromDate",
                    toDate: "toDate",
                    status: "status"                    
                },               
                
            };

            return backEndModel;
        }
    ]);