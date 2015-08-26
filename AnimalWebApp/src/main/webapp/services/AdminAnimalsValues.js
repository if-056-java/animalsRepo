angular.module('AdminAnimalsValues', [])
    .value('AdminAnimalsValues', {
        filter: {page: 1, limit: '15'},
        totalItems: {count: 0},
        animals: {values: []},
        animalTypes: {values: []},
        animalServices: {values: []}
    });
