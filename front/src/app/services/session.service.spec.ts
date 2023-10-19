import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { SessionService } from './session.service';
import { SessionInformation } from '../interfaces/sessionInformation.interface';

describe('SessionService', () => {
  let service: SessionService;

  // On mock les données pour les tests
  const mockUser: SessionInformation = {
    token: 'mockToken123',
    type: 'mockType',
    id: 1,
    username: 'mockUsername',
    firstName: 'John',
    lastName: 'Doe',
    admin: false
  };

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SessionService);
  });

  afterEach(() => {
    service.logOut();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // Test : On vérifie que l'utilisateur est bien connecté et et que ses informations sont enregistrées après l'appel à logIn
  it('should set isLogged to true and save session information when logIn is called', () => {
    service.logIn(mockUser);

    expect(service.isLogged).toBe(true);
    expect(service.sessionInformation).toEqual(mockUser);
  });

  // Test : On vérifie que l'utilisateur est bien déconnecté et que ses informations sont effacées après l'appel à logOut
  it('should set isLogged to false and clear session information when logOut is called', () => {
    service.logIn(mockUser);
    service.logOut();

    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });

  // Test : On vérifie que l'observable $isLogged émet bien la valeur de isLogged (ici, déconnecté par défaut)
  it('should emit the current logged in status when $isLogged is subscribed to', (done) => {
    service.$isLogged().subscribe(isLogged => {
      expect(isLogged).toBe(false);
      done();
    });
  });

  // Test : On vérifie que l'observable $isLogged émet bien la valeur de isLogged (ici, connecté après un appel à logIn)
  it('should emit true after logging in', (done) => {
    service.$isLogged().subscribe(isLogged => {
      if (isLogged) {
        expect(isLogged).toBe(true);
        done();
      }
    });

    service.logIn(mockUser);
  });
});
