angular.module('DoctorAnimalsValues', [])
    .value('DoctorAnimalsValues', {
        filter: {
            page: 1,
            limit: '15'
        },
        totalItems: {count: 0},
        animals: {values: []},
        animalTypes: {values: []},
        animalServices: {values: []},
        animal: {},
        medicalHistory: {
            filter: {
                page: 1,
                limit: '15'
            },
            totalItems: {
                count: 0
            },
            items: {
                values: []
            }
        }
    });
