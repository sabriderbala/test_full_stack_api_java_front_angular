describe('Detail Component Test', () => {
  const setupIntercepts = () => {
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: false,
        email: 'yoga@studio.com',
        createdAt: '2021-05-04T12:00:00.000Z',
        updatedAt: '2021-05-04T12:00:00.000Z'
      }
    }).as('loginApi');

    cy.intercept('GET', '/api/session', {
      headers: { Authorization: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b2dhQHN0dWRpby5jb20iLCJpYXQiOjE2OTY0NzAwODgsImV4cCI6MTY5NjU1NjQ4OH0.35vN01bMhA9V75VsGiQq54BYo4_A_dOfDqXiE2cv0dfyPpN5YDzimcf1LpLj1672Nj14SmpBUAB0ItoEIqwqwQ` },
      body: [
        {
            "id": 2,
            "name": "session A",
            "date": "2023-06-06T00:00:00.000+00:00",
            "teacher_id": 1,
            "description": "description A",
            "users": [
                4
            ],
            "createdAt": "2023-09-21T01:43:03",
            "updatedAt": "2023-09-21T01:43:20"
        },
        {
            "id": 3,
            "name": "session B",
            "date": "2012-01-01T00:00:00.000+00:00",
            "teacher_id": null,
            "description": "description B",
            "users": [],
            "createdAt": "2023-10-05T03:42:25",
            "updatedAt": "2023-10-05T03:42:25"
        },
        {
            "id": 4,
            "name": "session C",
            "date": "2012-01-01T00:00:00.000+00:00",
            "teacher_id": null,
            "description": "description C",
            "users": [],
            "createdAt": "2023-10-05T04:06:16",
            "updatedAt": "2023-10-05T04:06:16"
        }
    ]
    }).as('sessionApiGet');

    cy.intercept('GET', '/api/session/2', {
      headers: { Authorization: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b2dhQHN0dWRpby5jb20iLCJpYXQiOjE2OTY0NzAwODgsImV4cCI6MTY5NjU1NjQ4OH0.35vN01bMhA9V75VsGiQq54BYo4_A_dOfDqXiE2cv0dfyPpN5YDzimcf1LpLj1672Nj14SmpBUAB0ItoEIqwqwQ` },
      body: {
        "id": 2,
        "name": "session",
        "date": "2023-06-06T00:00:00.000+00:00",
        "teacher_id": 1,
        "description": "Superbe session",
        "users": [
            4, 6, 5, 7, 8, 9, 10, 11, 12, 13
        ],
        "createdAt": "2023-09-21T01:43:03",
        "updatedAt": "2023-09-21T01:43:20"
    }
    }).as('sessionApiGetDetail');

    cy.intercept('GET', '/api/teacher/1', {
      headers: { Authorization: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b2dhQHN0dWRpby5jb20iLCJpYXQiOjE2OTY0NzAwODgsImV4cCI6MTY5NjU1NjQ4OH0.35vN01bMhA9V75VsGiQq54BYo4_A_dOfDqXiE2cv0dfyPpN5YDzimcf1LpLj1672Nj14SmpBUAB0ItoEIqwqwQ` },
      body: {
        "id": 1,
        "lastName": "DELAHAYE",
        "firstName": "Margot",
        "createdAt": "2023-09-21T01:36:18",
        "updatedAt": "2023-09-21T01:36:18"
      }
    }).as('TeacherApiGet');

    cy.intercept('POST', '/api/session/2/participate/1', {
      headers: { Authorization: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b2dhQHN0dWRpby5jb20iLCJpYXQiOjE2OTY0NzAwODgsImV4cCI6MTY5NjU1NjQ4OH0.35vN01bMhA9V75VsGiQq54BYo4_A_dOfDqXiE2cv0dfyPpN5YDzimcf1LpLj1672Nj14SmpBUAB0ItoEIqwqwQ` },
    }).as('ParticipateApiPost');

    cy.intercept('DELETE', '/api/session/2/participate/1', {
      headers: { Authorization: `Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b2dhQHN0dWRpby5jb20iLCJpYXQiOjE2OTY0NzAwODgsImV4cCI6MTY5NjU1NjQ4OH0.35vN01bMhA9V75VsGiQq54BYo4_A_dOfDqXiE2cv0dfyPpN5YDzimcf1LpLj1672Nj14SmpBUAB0ItoEIqwqwQ` },
    }).as('ParticipateApiPost');
  };

  const goToDetailComponent = () => {
    cy.contains('Detail').click();
  };

  const loginUser = () => {
    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type('test!1234{enter}{enter}');
  };

  beforeEach(() => {
    setupIntercepts();
    cy.visit('/sessions/detail/2');
    loginUser();
  });

  it('Should navigate to a session detail', () => {
    goToDetailComponent();
    cy.url().should('include', '/sessions/detail/2');
  });

  it('Should display the session info', () => {
    goToDetailComponent();
    cy.get('h1').should('contain', 'Session');
    cy.get('span').should('contain', '10 attendees');
    cy.get('span').should('contain', 'June 6, 2023');
    cy.get('span').should('contain', 'Margot DELAHAYE');
  });

  it('Should participate', () => {
    goToDetailComponent();
    cy.get('span').contains('Participate').click();
  });

  it('Should back', () => {
    goToDetailComponent();
    cy.get('button').contains('arrow_back').click();
    cy.url().should('include', '/sessions');
  });

});
