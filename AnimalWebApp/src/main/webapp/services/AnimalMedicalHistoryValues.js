angular.module('AnimalMedicalHistoryValues', [])
    .value('AnimalMedicalHistoryValues', {
        filter: {
            page: 1,
            limit: '15'
        },
        totalItems: {count: 0},
        items: {values: []},
        item: {}
    });