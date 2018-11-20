'use strict';

// Register `topology` component, along with its associated controller and template
angular.
  module('topology', ['backEndService']).
  component('topology', {
    templateUrl: 'app/components/topology/topology.template.html',
    controller: ['$scope', '$window', 'BackEndService', 'BackEndModel',
      function TopologyController($scope, $window, BackEndService, BackEndModel) {
        $scope.network = null;

        const singsingColor = '#777';
        const mediumsecColor = '#80ccff';
        const highsecColor = '#0091F2';
        const prioritysecColor = '#302340';

        
        var createNetwork = function () {
          BackEndService.getAreas(function (result) {
            
            if(result.data.length == null || result.data.length < 1){
              return;
            }

            console.log(result.data);

            var nodesArray = [{id: 'SingSing', label: 'SingSing', group: 'singsing'}];
            var edgesArray = [];

            for (var i = 0; i < result.data.length; i++) {
              var area = result.data[i];

              var areaGroup = '';
              switch (area.areaSecurity) {
                case 'Priority':
                  areaGroup = 'prioritysecArea';
                  break;
                case 'High':
                  areaGroup = 'highsecArea';
                  break;
                default:
                  areaGroup = 'mediumsecArea';
                  break;
              }

              nodesArray.push({
                id: 'A' + i, label: area.name, 
                title: '<b>Id:</b> ' + area.id + '<br /><b>Security level:</b> ' + area.areaSecurity, 
                group: areaGroup
              });
              edgesArray.push({from: 'SingSing', to: 'A' + i, color: {color: singsingColor, opacity:0.5}});

              if(area.prisonCells.length > 0){
                for (var j = 0; j < area.prisonCells.length; j++) {
                  var prisonCell = area.prisonCells[j];

                  var cellGroup = '';
                  switch (area.areaSecurity) {
                    case 'Priority':
                      cellGroup = 'prioritysecCell';
                      break;
                    case 'High':
                      cellGroup = 'highsecCell';
                      break;
                    default:
                      cellGroup = 'mediumsecCell';
                      break;
                  }
    
                  nodesArray.push({
                    id: 'C' + i + '-' + j, 
                    label: 'Cell: ' + prisonCell.prisoners.length +  '/' + prisonCell.space + '\nFloor: ' + prisonCell.floor, 
                    title: '<b>Id:</b> ' + prisonCell.id + '<br /><b>Description:</b> ' + prisonCell.cellDesc, 
                    group: cellGroup
                  });
                  edgesArray.push({from: 'A' + i, to: 'C' + i + '-' + j, color: {color: singsingColor, opacity:0.5}});
                  
                  if(prisonCell.prisoners.length > 0){
                    for (var k = 0; k < prisonCell.prisoners.length; k++) {
                      var prisoner = prisonCell.prisoners[k];


                      var prisonerGroup = '';
                      switch (prisoner.prisonerSecurity) {
                        case 'Violent':
                          prisonerGroup = 'prioritysecPrisoner';
                          break;
                        case 'Dangerous':
                          prisonerGroup = 'highsecPrisoner';
                          break;
                        default:
                          prisonerGroup = 'mediumsecPrisoner';
                          break;
                      }

                      var prisonerRelease = new Date(prisoner.releaseDate);
                      nodesArray.push({
                        id: 'P' + i + '-' + j + '-' + k, 
                        label: prisoner.prisonerName,
                        title: '<b>Id:</b> ' + prisoner.id + '<br /><b>Incident:</b> ' + prisoner.incident + '<br /><b>Security level:</b> ' + prisoner.prisonerSecurity + '<br /><b>Release date:</b> ' + prisonerRelease.toDateString(), 
                        group: prisonerGroup
                      });
                      edgesArray.push({from: 'C' + i + '-' + j, to: 'P' + i + '-' + j + '-' + k, color: {color: singsingColor, opacity:0.5}});    
                    }
                  }
                }
              }
            }
            
            // create a network
            var container = $window.document.getElementById('singsingTopology');
            var data = {
              nodes: new $window.vis.DataSet(nodesArray),
              edges: new $window.vis.DataSet(edgesArray)
            };
            var options = {
              interaction:{hover:true},
              groups: {
                singsing: {
                  shape: 'icon',
                  icon: {
                    face: 'FontAwesome',
                    code: '\uf286',
                    size: 50,
                    color: singsingColor
                  }
                },
                mediumsecArea: {
                  shape: 'icon',
                  icon: {
                    face: 'FontAwesome',
                    code: '\uf041',
                    size: 30,
                    color: mediumsecColor
                  }
                },
                highsecArea: {
                  shape: 'icon',
                  icon: {
                    face: 'FontAwesome',
                    code: '\uf041',
                    size: 30,
                    color: highsecColor
                  }
                },
                prioritysecArea: {
                  shape: 'icon',
                  icon: {
                    face: 'FontAwesome',
                    code: '\uf041',
                    size: 30,
                    color: prioritysecColor
                  }
                },
                mediumsecCell: {
                  shape: 'icon',
                  icon: {
                    face: 'FontAwesome',
                    code: '\uf0c9',
                    size: 30,
                    color: mediumsecColor
                  }
                },
                highsecCell: {
                  shape: 'icon',
                  icon: {
                    face: 'FontAwesome',
                    code: '\uf0c9',
                    size: 30,
                    color: highsecColor
                  }
                },
                prioritysecCell: {
                  shape: 'icon',
                  icon: {
                    face: 'FontAwesome',
                    code: '\uf0c9',
                    size: 30,
                    color: prioritysecColor
                  }
                },
                mediumsecPrisoner: {
                  shape: 'icon',
                  icon: {
                    face: 'FontAwesome',
                    code: '\uf007',
                    size: 30,
                    color: mediumsecColor
                  }
                },
                highsecPrisoner: {
                  shape: 'icon',
                  icon: {
                    face: 'FontAwesome',
                    code: '\uf007',
                    size: 30,
                    color: highsecColor
                  }
                },
                prioritysecPrisoner: {
                  shape: 'icon',
                  icon: {
                    face: 'FontAwesome',
                    code: '\uf007',
                    size: 30,
                    color: prioritysecColor
                  }
                }
              },
            };

            $scope.network = new $window.vis.Network(container, data, options);

          }, function (error) {
            $scope.network = null;
            console.log(error);
          });
        };

        createNetwork();
	    }]
  });
