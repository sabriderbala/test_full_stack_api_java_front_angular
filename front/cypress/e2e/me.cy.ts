describe('Me Component Test', () => {

  const goToMeComponent = () => {
    cy.get('span[routerlink=me]').as('meLink').click();
  };

  beforeEach(() => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      statusCode: 200,
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true,
        email: 'yoga@studio.com',
        createdAt: '2021-05-04T12:00:00.000Z',
        updatedAt: '2021-05-04T12:00:00.000Z'
      }
    }).as('loginApi');

    cy.intercept('GET', '/api/session', {
      statusCode: 200
    }).as('sessionApi');

    cy.intercept('GET', '/api/user/1', {
      statusCode: 200,
      body: {
        id: 1,
        firstName: 'Admin ADMIN',
        email: 'yoga@studio.com',
        admin: false,
        createdAt: '2023-10-04T12:00:00.000Z',
        updatedAt: '2023-10-04T12:00:00.000Z'
      }
    }).as('meApi');

    cy.intercept('DELETE', '/api/user/1', {
      statusCode: 200
    }).as('deleteApi');

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type('test!1234{enter}{enter}');
    cy.url().should('include', '/sessions');
  });

  it('Should navigate to me-component', () => {
    goToMeComponent();
    cy.url().should('include', '/me');
  });

  describe('When on the Me Component', () => {

    beforeEach(() => {
      goToMeComponent();
    });

    it('Should display user data', () => {
      cy.get('p').contains('Admin ADMIN');
      cy.get('p').contains('yoga@studio.com');
    });

    it('Should navigate back from me component to session', () => {
      cy.get('button').contains('arrow_back').click();
      cy.url().should('include', '/sessions');
    });

    it('Should delete user and navigate to home', () => {
      cy.contains('delete').click();
      cy.url().should('include', '/');
      cy.contains('Your account has been deleted !');
    });
  });
});
