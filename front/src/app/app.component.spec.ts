import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from './services/session.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';

import { AppComponent } from './app.component';


describe('AppComponent', () => {

  let component: AppComponent;
  let fixture: any;
  let mockSessionService: any;
  let mockRouter: any;

  beforeEach(async () => {

    // on crée des mocks pour les services
    mockSessionService = {
      $isLogged: jest.fn().mockReturnValue(of(true)),
      logOut: jest.fn()
    };

    // on crée un mock pour le router
    mockRouter = {
      navigate: jest.fn()
    };

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatToolbarModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: Router, useValue: mockRouter }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
  });

  // Test : on vérifie que le composant est bien créé
  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  // Test : on vérifie que isLogged est bien appelé
  it('should return logged status', () => {
    const isLogged = component.$isLogged();
    isLogged.subscribe(status => {
      expect(status).toBe(true);
    });
    expect(mockSessionService.$isLogged).toHaveBeenCalled();
  });

  // Test : on vérifie que logout appelle bien logOut et navigate
  it('should log out and navigate to root', () => {
    component.logout();
    expect(mockSessionService.logOut).toHaveBeenCalled();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['']);
  });

});
