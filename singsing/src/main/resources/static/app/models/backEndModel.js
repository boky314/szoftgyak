'use strict';

angular.
    module('backEndModel', []).
    factory('BackEndModel', [
        function () {

            const backEndModel = {

                area: {
                    id: "id",
                    name: "name",
                    areaSecurity : "areaSecurity"
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
                    prisonerSecurity: "prisonerSecurity"	
                },

                prisonGuard: {
                    name: "prisonGuardName"
                }
            };

            return backEndModel;
        }
    ]);