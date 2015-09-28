angular.module('AdminUsersValues', [])
    .value('AdminUsersValues', {
        filter: {page: 1, limit: '10'},
        totalItems: {count: 0},
        userRoles: {values: []},
        userTypes: {values: []},
        users: {values: []}
    });
