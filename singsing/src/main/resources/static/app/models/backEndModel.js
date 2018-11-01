'use strict';

angular.
    module('backEndModel', []).
    factory('BackEndModel', [
        function () {

            const backEndModel = {

                area: {
                    id: "id",
                    name: "name"
                },

                prisoncell: {
                    id: "id",
                    space: "space",
                    cellDescription: "cellDesc",
                    floor: "floor",
                    prisoners: "prisoners",
                    areaId: "areaId"
                },

                prisoner: {
                    id: "id",
                    prisonerName: "prisonerName",
                    incident: "incident",
                    releaseDate: "releaseDate",
                    placeDate: "placeDate",
                    cellId: "cellId"
                },

                prisonGuard: {
                    name: "name"
                }
            };

            return backEndModel;
        }
    ]);
