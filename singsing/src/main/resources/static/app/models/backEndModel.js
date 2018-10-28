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
                    area: "area"
                },

                prisoner: {
                    id: "id",
                    prisonerName: "prisonerName",
                    incident: "incident",
                    releaseDate: "releaseDate",
                    placeDate: "placeDate",
                    cellId: "cellId",
                    cell: "cell"
                },

                prisonGuard: {
                    name: "name"
                }
            };

            return backEndModel;
        }
    ]);
